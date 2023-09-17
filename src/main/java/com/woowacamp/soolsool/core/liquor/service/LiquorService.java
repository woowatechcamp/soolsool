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

    @Transactional(readOnly = true)
    public LiquorDetailResponse liquorDetail(final Long liquorId) {
        final Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));

        final List<Liquor> relatedLiquors = liquorRepository
            .findLiquorsPurchasedTogether(liquorId, TOP_RANK_PAGEABLE);

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
        final Long cursorId
    ) {
        final LiquorSearchCondition liquorSearchCondition = new LiquorSearchCondition(
            liquorRegionCache.findByType(regionType).orElse(null),
            liquorBrewCache.findByType(brewType).orElse(null),
            liquorStatusCache.findByType(statusType).orElse(null),
            brand
        );

        List<Liquor> liquors = liquorQueryDslRepository
            .getList(liquorSearchCondition, pageable, cursorId);

        liquors.stream()
            .map(Liquor::getId)
            .sorted()
            .forEach(liquorCtrRedisRepository::increaseImpression);

        return PageLiquorResponse.of(pageable, liquors);
    }

    @Transactional
    public PageLiquorResponse getFirstPage(final Pageable pageable) {
        final List<Liquor> liquors = liquorQueryDslRepository.getCachedList(pageable);

        liquors.stream()
            .map(Liquor::getId)
            .sorted()
            .forEach(liquorCtrRedisRepository::increaseImpression);

        return PageLiquorResponse.of(pageable, liquors);
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
        return liquorStatusCache.findByType(LiquorStatusType.valueOf(name))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_STATUS_FOUND));
    }

    private LiquorRegion getLiquorRegionByName(final String name) {
        return liquorRegionCache.findByType(LiquorRegionType.valueOf(name))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_REGION_FOUND));
    }

    private LiquorBrew getLiquorBrewBrewByName(final String name) {
        return liquorBrewCache.findByType(LiquorBrewType.valueOf(name))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_BREW_FOUND));
    }
}
