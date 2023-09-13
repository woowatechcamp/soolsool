package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_BREW_FOUND;
import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_FOUND;
import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_REGION_FOUND;
import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_STATUS_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorCtr;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSearchCondition;
import com.woowacamp.soolsool.core.liquor.dto.PageLiquorResponse;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorCtrRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorQueryDslRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusCache;
import com.woowacamp.soolsool.core.liquor.repository.redisson.LiquorCtrRedisRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LiquorService {

    private static final PageRequest TOP_RANK_PAGEABLE = PageRequest.of(0, 5);

    private final LiquorRepository liquorRepository;
    private final LiquorBrewCache liquorBrewCache;
    private final LiquorStatusCache liquorStatusCache;
    private final LiquorRegionCache liquorRegionCache;
    private final LiquorCtrRepository liquorCtrRepository;
    private final LiquorQueryDslRepository liquorQueryDslRepository;

    private final LiquorCtrRedisRepository liquorCtrRedisRepository;

    @CacheEvict(value = "liquorsFirstPage")
    @Transactional
    public Long saveLiquor(final LiquorSaveRequest request) {
        final LiquorBrew liquorBrew = getLiquorBrewBrewByName(request.getBrew());
        final LiquorRegion liquorRegion = getLiquorRegionByName(request.getRegion());
        final LiquorStatus liquorStatus = getLiquorStatusByName(request.getStatus());

        final Liquor liquor = liquorRepository
            .save(request.toEntity(liquorBrew, liquorRegion, liquorStatus));

        liquorCtrRepository.save(new LiquorCtr(liquor.getId()));

        return liquor.getId();
    }

    @Transactional
    public LiquorDetailResponse liquorDetail(final Long liquorId) {
        final Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));

        final List<Liquor> relatedLiquors =
            liquorRepository.findLiquorsPurchasedTogether(liquorId, TOP_RANK_PAGEABLE);

        liquorCtrRedisRepository.increaseClick(liquorId);

        return LiquorDetailResponse.of(liquor, relatedLiquors);
    }

    @Transactional
    public PageLiquorResponse liquorList(
        final LiquorBrewType brewType,
        final LiquorRegionType regionType,
        final LiquorStatusType statusType,
        final String brand,
        final Pageable pageable,
        final Long cursorId,
        final Long clickCount
    ) {
        final LiquorSearchCondition liquorSearchCondition = new LiquorSearchCondition(
            findLiquorRegionByType(regionType).orElse(null),
            findLiquorBrewByType(brewType).orElse(null),
            findLiquorStatusByType(statusType).orElse(null),
            brand
        );

        final List<LiquorElementResponse> liquors = liquorQueryDslRepository
            .getList(liquorSearchCondition, pageable, cursorId,clickCount);

        liquors.stream()
            .map(LiquorElementResponse::getId)
            .sorted()
            .forEach(liquorCtrRedisRepository::increaseImpression);

        return getPageLiquorResponse(pageable, liquors);
    }

    @Transactional
    public PageLiquorResponse getFirstPage(final Pageable pageable) {
        final List<LiquorElementResponse> liquors = liquorQueryDslRepository
            .getCachedList(pageable);

        liquors.stream()
            .map(LiquorElementResponse::getId)
            .sorted()
            .forEach(liquorCtrRedisRepository::increaseImpression);

        return getPageLiquorResponse(pageable, liquors);
    }

    private PageLiquorResponse getPageLiquorResponse(
        final Pageable pageable,
        final List<LiquorElementResponse> liquors
    ) {
        if (liquors.size() < pageable.getPageSize()) {
            return PageLiquorResponse.of(false, liquors);
        }

        final Long lastReadLiquorId = liquors.get(liquors.size() - 1).getId();
        final Long lastReadLiquorClickCount = liquors.get(liquors.size() - 1).getClickCount();

        return PageLiquorResponse.of(true, lastReadLiquorId, lastReadLiquorClickCount, liquors);
    }

    @CacheEvict(value = "liquorsFirstPage")
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

    @CacheEvict(value = "liquorsFirstPage")
    @Transactional
    public void deleteLiquor(final Long liquorId) {
        final Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));

        liquorRepository.delete(liquor);
    }


    @CacheEvict(value = "liquorsFirstPage")
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

    private Optional<LiquorStatus> findLiquorStatusByType(final LiquorStatusType statusType) {
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
