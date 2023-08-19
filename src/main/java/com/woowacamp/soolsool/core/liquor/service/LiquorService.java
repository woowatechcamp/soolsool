package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.core.liquor.exception.LiquorErrorCode.NOT_LIQUOR_BREW_FOUND;
import static com.woowacamp.soolsool.core.liquor.exception.LiquorErrorCode.NOT_LIQUOR_FOUND;
import static com.woowacamp.soolsool.core.liquor.exception.LiquorErrorCode.NOT_LIQUOR_REGION_FOUND;
import static com.woowacamp.soolsool.core.liquor.exception.LiquorErrorCode.NOT_LIQUOR_STATUS_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrand;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;
    private final LiquorStatusRepository liquorStatusRepository;
    private final LiquorRegionRepository liquorRegionRepository;
    private final LiquorBrewRepository liquorBrewRepository;

    @Transactional
    public Long saveLiquor(final LiquorSaveRequest request) {
        final LiquorBrew liquorBrew = getBrew(request.getBrew());
        final LiquorRegion liquorRegion = getRegion(request.getRegion());
        final LiquorStatus liquorStatus = getStatus(request.getStatus());

        final Liquor liquor = request.toEntity(liquorBrew, liquorRegion, liquorStatus);

        return liquorRepository.save(liquor).getId();
    }

    @Transactional(readOnly = true)
    public LiquorDetailResponse liquorDetail(final Long liquorId) {
        final Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));

        return LiquorDetailResponse.from(liquor);
    }

    @Transactional(readOnly = true)
    public List<LiquorElementResponse> liquorList(
        final LiquorBrewType brewType,
        final LiquorRegionType regionType,
        final LiquorStatusType statusType,
        final String brand,
        final Pageable pageable
    ) {
        final Specification<Liquor> conditions = searchWith(
            findLiquorBrewByType(brewType),
            findLiquorRegionByType(regionType),
            findLiquorStatusByType(statusType),
            brand
        );

        final Page<Liquor> liquors = liquorRepository.findAll(conditions, pageable);

        return liquors.getContent().stream()
            .map(LiquorElementResponse::from)
            .collect(Collectors.toList());
    }

    private Specification<Liquor> searchWith(
        final Optional<LiquorBrew> brew,
        final Optional<LiquorRegion> region,
        final Optional<LiquorStatus> status,
        final String brand
    ) {
        return ((root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            brew.ifPresent(liquorBrew -> predicates
                .add(criteriaBuilder.equal(root.get("brew"), liquorBrew)));

            region.ifPresent(liquorRegion -> predicates
                .add(criteriaBuilder.equal(root.get("region"), liquorRegion)));

            status.ifPresent(liquorStatus -> predicates
                .add(criteriaBuilder.equal(root.get("status"), liquorStatus)));

            if (StringUtils.hasText(brand)) {
                predicates.add(criteriaBuilder.equal(root.get("brand"), new LiquorBrand(brand)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Transactional
    public void modifyLiquor(final Long liquorId, final LiquorModifyRequest liquorModifyRequest) {
        final Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));

        final LiquorBrew modifyLiquorBrew = getBrew(liquorModifyRequest.getTypeName());
        final LiquorRegion modifyLiquorRegion = getRegion(liquorModifyRequest.getRegionName());
        final LiquorStatus modifyLiquorStatus = getStatus(liquorModifyRequest.getStatusName());

        liquor.update(
            modifyLiquorBrew, modifyLiquorRegion,
            modifyLiquorStatus, liquorModifyRequest
        );
    }

    @Transactional
    public void deleteLiquor(final Long liquorId) {
        Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));
        liquorRepository.delete(liquor);
    }

    private LiquorStatus getStatus(final String request) {
        return findLiquorStatusByType(LiquorStatusType.valueOf(request))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_STATUS_FOUND));
    }

    private LiquorRegion getRegion(final String request) {
        return findLiquorRegionByType(LiquorRegionType.valueOf(request))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_REGION_FOUND));
    }

    private LiquorBrew getBrew(final String request) {
        return findLiquorBrewByType(LiquorBrewType.valueOf(request))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_BREW_FOUND));
    }

    private Optional<LiquorStatus> findLiquorStatusByType(final LiquorStatusType statusType) {
        return liquorStatusRepository.findByType(statusType);
    }

    private Optional<LiquorRegion> findLiquorRegionByType(final LiquorRegionType regionType) {
        return liquorRegionRepository.findByType(regionType);
    }

    private Optional<LiquorBrew> findLiquorBrewByType(final LiquorBrewType brewType) {
        return liquorBrewRepository.findByType(brewType);
    }
}
