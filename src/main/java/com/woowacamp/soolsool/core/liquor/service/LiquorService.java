package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_BREW_TYPE_FOUND;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_FOUND;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_REGION_TYPE_FOUND;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_STATUS_TYPE_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.dto.ModifyLiquorRequest;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorTypeRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;
    private final LiquorStatusRepository liquorStatusRepository;
    private final LiquorRegionRepository liquorRegionRepository;
    private final LiquorTypeRepository liquorTypeRepository;

    @Transactional
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

    @Transactional
    public void modifyLiquor(Long liquorId, ModifyLiquorRequest modifyLiquorRequest) {
        Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));

        LiquorType modifyLiquorType = liquorTypeRepository
            .findByType(LiquorBrewType.findType(modifyLiquorRequest.getTypeName()))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_BREW_TYPE_FOUND));

        LiquorRegion modifyLiquorRegion = liquorRegionRepository
            .findByType(LiquorRegionType.findType(modifyLiquorRequest.getRegionName()))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_REGION_TYPE_FOUND));

        LiquorStatus modifyLiquorStatus = liquorStatusRepository
            .findByType(LiquorStatusType.findType(modifyLiquorRequest.getStatusName()))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_STATUS_TYPE_FOUND));

        liquor.update(
            modifyLiquorType, modifyLiquorRegion,
            modifyLiquorStatus, modifyLiquorRequest);
    }

    
}
