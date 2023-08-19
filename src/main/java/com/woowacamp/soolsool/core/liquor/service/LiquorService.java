package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrand;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorTypeRepository;
import com.woowacamp.soolsool.global.exception.LiquorErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;
    private final LiquorStatusRepository liquorStatusRepository;
    private final LiquorRegionRepository liquorRegionRepository;
    private final LiquorBrewRepository liquorBrewRepository;

    @Transactional
    public Long saveLiquor(final LiquorSaveRequest request) {
        LiquorType liquorType = liquorTypeRepository
            .findByType(LiquorBrewType.valueOf(request.getTypeName()))
            .orElseThrow();
        LiquorRegion liquorRegion = liquorRegionRepository
            .findByType(LiquorRegionType.valueOf(request.getRegionName()))
            .orElseThrow();
        LiquorStatus liquorStatus = liquorStatusRepository
            .findByType(LiquorStatusType.valueOf(request.getStatusName()))
            .orElseThrow();
        final LiquorBrew liquorBrew = getBrew(request.getBrew());
        final LiquorRegion liquorRegion = getRegion(request.getRegion());
        final LiquorStatus liquorStatus = getStatus(request.getStatus());

        final Liquor liquor = request.toEntity(liquorBrew, liquorRegion, liquorStatus);

        return liquorRepository.save(liquor).getId();
    }

    @Transactional(readOnly = true)
    public LiquorDetailResponse liquorDetail(final Long liquorId) {
        final Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(LiquorErrorCode.NOT_LIQUOR_FOUND));

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
        final LiquorType liquorType = liquorTypeRepository.findByType(brewType).orElse(null);
        final LiquorRegion liquorRegion = liquorRegionRepository.findByType(regionType)
            .orElse(null);
        final LiquorStatus liquorStatus = liquorStatusRepository.findByType(statusType)
            .orElse(null);

        final Specification<Liquor> conditions = searchWith(liquorType, liquorRegion, liquorStatus,
            brand);

        final Page<Liquor> liquors = liquorRepository.findAll(conditions, pageable);

        return liquors.getContent().stream()
            .map(LiquorElementResponse::from)
            .collect(Collectors.toList());
    }

    private Specification<Liquor> searchWith(
        final LiquorType liquorType,
        final LiquorRegion liquorRegion,
        final LiquorStatus liquorStatus,
        final String brand
    ) {
        return ((root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (liquorType != null) {
                predicates.add(criteriaBuilder.equal(root.get("liquorType"), liquorType));
            }

            if (liquorRegion != null) {
                predicates.add(criteriaBuilder.equal(root.get("liquorRegion"), liquorRegion));
            }

            if (liquorStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("liquorStatus"), liquorStatus));
            }

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
        return liquorStatusRepository
            .findByType(LiquorStatusType.valueOf(request));
    }

    private LiquorRegion getRegion(final String request) {
        return liquorRegionRepository
            .findByType(LiquorRegionType.valueOf(request));
    }

    private LiquorBrew getBrew(final String request) {
        return liquorBrewRepository
            .findByType(LiquorBrewType.valueOf(request));
    }
}
