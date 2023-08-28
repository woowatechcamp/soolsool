package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_BREW_FOUND;
import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_FOUND;
import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_REGION_FOUND;
import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_STATUS_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSearchCondition;
import com.woowacamp.soolsool.core.liquor.dto.PageLiquorResponse;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorQueryDslRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusCache;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LiquorService {

    private static final PageRequest TOP_RANK_PAGEABLE = PageRequest.of(0, 5);

    private final LiquorRepository liquorRepository;
    private final LiquorBrewCache liquorBrewCache;
    private final LiquorStatusCache liquorStatusCache;
    private final LiquorRegionCache liquorRegionCache;
    private final LiquorQueryDslRepository liquorQueryDslRepository;


    @Transactional
    public Long saveLiquor(final LiquorSaveRequest request) {
        final LiquorBrew liquorBrew = getLiquorBrewBrewByName(request.getBrew());
        final LiquorRegion liquorRegion = getLiquorRegionByName(request.getRegion());
        final LiquorStatus liquorStatus = getLiquorStatusByName(request.getStatus());

        final Liquor liquor = request.toEntity(liquorBrew, liquorRegion, liquorStatus);

        return liquorRepository.save(liquor).getId();
    }

    @Transactional(readOnly = true)
    public LiquorDetailResponse liquorDetail(final Long liquorId) {
        final Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));

        final List<Liquor> relatedLiquors =
            liquorRepository.findLiquorsPurchasedTogether(liquorId, TOP_RANK_PAGEABLE);

        return LiquorDetailResponse.of(liquor, relatedLiquors);
    }

    @Transactional(readOnly = true)
    public PageLiquorResponse liquorList(
        final LiquorBrewType brewType,
        final LiquorRegionType regionType,
        final LiquorStatusType statusType,
        final String brand,
        final Pageable pageable,
        final Long cursorId
    ) {
        final LiquorSearchCondition liquorSearchCondition = new LiquorSearchCondition
            (
                findLiquorRegionByType(regionType),
                findLiquorBrewByType(brewType),
                findLiquorStatusByType(statusType),
                brand
            );

        final List<LiquorElementResponse> liquors = liquorQueryDslRepository
            .getList(liquorSearchCondition, pageable, cursorId);

        if (liquors.size() < pageable.getPageSize()) {
            return PageLiquorResponse.of(false, liquors);
        }

        return PageLiquorResponse.of(true, liquors.get(liquors.size() - 1).getId(), liquors);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "liquorsFirstPage")
    public PageLiquorResponse getFirstPage(final Pageable pageable) {
        final LiquorSearchCondition liquorSearchCondition = new LiquorSearchCondition
            (
                findLiquorRegionByType(null),
                findLiquorBrewByType(null),
                findLiquorStatusByType(null),
                null
            );

        final List<LiquorElementResponse> liquors = liquorQueryDslRepository
            .getList(liquorSearchCondition, pageable, null);
        
        if (liquors.size() < pageable.getPageSize()) {
            return PageLiquorResponse.of(false, liquors);
        }
        return PageLiquorResponse.of(true, liquors.get(liquors.size() - 1).getId(), liquors);
    }

    @Transactional
    public void modifyLiquor(final Long liquorId, final LiquorModifyRequest liquorModifyRequest) {
        final Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));

        final LiquorBrew modifyLiquorBrew = getLiquorBrewBrewByName(
            liquorModifyRequest.getTypeName());
        final LiquorRegion modifyLiquorRegion = getLiquorRegionByName(
            liquorModifyRequest.getRegionName());
        final LiquorStatus modifyLiquorStatus = getLiquorStatusByName(
            liquorModifyRequest.getStatusName());

        liquor.update(
            modifyLiquorBrew, modifyLiquorRegion,
            modifyLiquorStatus, liquorModifyRequest
        );
    }

    @Transactional
    public void deleteLiquor(final Long liquorId) {
        final Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));

        liquorRepository.delete(liquor);
    }

    @Transactional
    public void decreaseTotalStock(final Long liquorId, final int quantity) {
        liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND))
            .decreaseTotalStock(quantity);
    }

    private LiquorStatus getLiquorStatusByName(final String name) {
        return findLiquorStatusByType(LiquorStatusType.valueOf(name))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_STATUS_FOUND));
    }

    private LiquorRegion getLiquorRegionByName(final String name) {
        return findLiquorRegionByType(LiquorRegionType.valueOf(name))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_REGION_FOUND));
    }

    private LiquorBrew getLiquorBrewBrewByName(final String name) {
        return findLiquorBrewByType(LiquorBrewType.valueOf(name))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_BREW_FOUND));
    }

    public Optional<LiquorStatus> findLiquorStatusByType(final LiquorStatusType statusType) {
        if (Objects.isNull(statusType)) {
            return Optional.empty();
        }
        return liquorStatusCache.findByType(statusType);
    }

    private Optional<LiquorRegion> findLiquorRegionByType(final LiquorRegionType regionType) {
        if (Objects.isNull(regionType)) {
            return Optional.empty();
        }
        return liquorRegionCache.findByType(regionType);
    }

    private Optional<LiquorBrew> findLiquorBrewByType(final LiquorBrewType brewType) {
        if (Objects.isNull(brewType)) {
            return Optional.empty();
        }
        return liquorBrewCache.findByType(brewType);
    }
}
