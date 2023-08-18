package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_BREW_TYPE_FOUND;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_REGION_TYPE_FOUND;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_STATUS_TYPE_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrand;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;
    private final LiquorStatusRepository liquorStatusRepository;
    private final LiquorRegionRepository liquorRegionRepository;
    private final LiquorTypeRepository liquorTypeRepository;

    public Long saveLiquor(final SaveLiquorRequest request) {
        LiquorType liquorType = liquorTypeRepository
            .findByType(LiquorBrewType.findType(request.getTypeName()))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_BREW_TYPE_FOUND));

        LiquorRegion liquorRegion = liquorRegionRepository
            .findByType(LiquorRegionType.findType(request.getRegionName()))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_REGION_TYPE_FOUND));

        LiquorStatus liquorStatus = liquorStatusRepository
            .findByType(LiquorStatusType.findType(request.getStatusName()))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_STATUS_TYPE_FOUND));

        Liquor liquor = Liquor.of(
            liquorType, liquorRegion,
            liquorStatus, request
        );
        return liquorRepository.save(liquor).getId();
    }

    public LiquorDetailResponse liquorDetail(final Long liquorId) {
        final Liquor liquor = liquorRepository.findById(liquorId)
                .orElseThrow(() -> new SoolSoolException(LiquorErrorCode.NOT_LIQUOR_FOUND));

        return new LiquorDetailResponse(
                liquor.getId(),
                liquor.getName().getName(),
                liquor.getPrice().getPrice().toString(),
                liquor.getBrand().getBrand(),
                liquor.getImageUrl().getImageUrl(),
                liquor.getStock().getStock(),
                liquor.getAlcohol().getAlcohol(),
                liquor.getVolume().getVolume()
        );
    }

    public List<LiquorElementResponse> liquorList(
            final LiquorBrewType brewType,
            final LiquorRegionType regionType,
            final LiquorStatusType statusType,
            final String brand,
            final Pageable pageable
    ) {
        final LiquorType liquorType = liquorTypeRepository.findByType(brewType).orElse(null);
        final LiquorRegion liquorRegion = liquorRegionRepository.findByType(regionType).orElse(null);
        final LiquorStatus liquorStatus = liquorStatusRepository.findByType(statusType).orElse(null);

        final Specification<Liquor> conditions = searchWith(liquorType, liquorRegion, liquorStatus, brand);

        final Page<Liquor> liquors = liquorRepository.findAll(conditions, pageable);

        return liquors.getContent().stream()
                .map(liquor -> new LiquorElementResponse(
                        liquor.getId(),
                        liquor.getName().getName(),
                        liquor.getPrice().getPrice().toString(),
                        liquor.getImageUrl().getImageUrl(),
                        liquor.getStock().getStock()
                ))
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
}
