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
import com.woowacamp.soolsool.core.liquor.dto.request.LiquorListRequest;
import com.woowacamp.soolsool.core.liquor.dto.request.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.request.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.dto.request.LiquorSearchCondition;
import com.woowacamp.soolsool.core.liquor.dto.response.LiquorClickElementDto;
import com.woowacamp.soolsool.core.liquor.dto.response.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.response.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.response.PageLiquorResponse;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorCtrRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorQueryDslRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusCache;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.stream.Collectors;
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

        return LiquorDetailResponse.of(liquor);
    }

    @Transactional(readOnly = true)
    public List<LiquorElementResponse> liquorPurchasedTogether(final Long liquorId) {
        final List<Liquor> relatedLiquors = liquorRepository
            .findLiquorsPurchasedTogether(liquorId, TOP_RANK_PAGEABLE);

        return relatedLiquors.stream()
            .map(LiquorElementResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public PageLiquorResponse liquorListByClick(
        final LiquorListRequest liquorListRequest,
        final Pageable pageable
    ) {
        final LiquorSearchCondition liquorSearchCondition = new LiquorSearchCondition(
            liquorRegionCache.findByType(liquorListRequest.getRegion()).orElse(null),
            liquorBrewCache.findByType(liquorListRequest.getBrew()).orElse(null),
            liquorStatusCache.findByType(liquorListRequest.getStatus()).orElse(null),
            liquorListRequest.getBrand()
        );

        final List<LiquorClickElementDto> liquors = liquorQueryDslRepository
            .getListByClick(
                liquorSearchCondition, pageable,
                liquorListRequest.getLiquorId(),
                liquorListRequest.getClickCount()
            );

        return PageLiquorResponse.of(pageable, getLiquorElementResponseFromClick(liquors));
    }

    private List<LiquorElementResponse> getLiquorElementResponseFromClick(
        List<LiquorClickElementDto> liquors) {
        return liquors.stream().map(
            liquor -> new LiquorElementResponse(
                liquor.getId(),
                liquor.getName(),
                liquor.getPrice(),
                liquor.getImageUrl(),
                liquor.getStock()
            )
        ).collect(Collectors.toList());
    }


    @Transactional
    public PageLiquorResponse liquorListByLatest(
        final LiquorListRequest liquorListRequest,
        final Pageable pageable
    ) {
        final LiquorSearchCondition liquorSearchCondition = new LiquorSearchCondition(
            liquorRegionCache.findByType(liquorListRequest.getRegion()).orElse(null),
            liquorBrewCache.findByType(liquorListRequest.getBrew()).orElse(null),
            liquorStatusCache.findByType(liquorListRequest.getStatus()).orElse(null),
            liquorListRequest.getBrand()
        );

        final List<Liquor> liquors = liquorQueryDslRepository.getList(
            liquorSearchCondition, pageable,
            liquorListRequest.getLiquorId()
        );

        return PageLiquorResponse.of(pageable, getLiquorElementsFromLiquor(liquors));
    }

    private static List<LiquorElementResponse> getLiquorElementsFromLiquor(List<Liquor> liquors) {
        return liquors.stream()
            .map(LiquorElementResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public PageLiquorResponse getFirstPage(final Pageable pageable) {
        final List<Liquor> liquors = liquorQueryDslRepository.getCachedList(pageable);

        return PageLiquorResponse.of(pageable, getLiquorElementsFromLiquor(liquors));
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
