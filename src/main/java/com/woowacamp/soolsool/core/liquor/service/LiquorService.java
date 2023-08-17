package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_BREW_TYPE_FOUND;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_REGION_TYPE_FOUND;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_STATUS_TYPE_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;
    private final LiquorStatusRepository liquorStatusRepository;
    private final LiquorRegionRepository liquorRegionRepository;
    private final LiquorTypeRepository liquorTypeRepository;

    public Long saveLiquor(SaveLiquorRequest request) {
        LiquorRegionType liquorRegionType = LiquorRegionType.valueOf(request.getRegionName());
        LiquorBrewType type = LiquorBrewType.valueOf(request.getTypeName());
        LiquorStatusType statusType = LiquorStatusType.valueOf(request.getStatusName());

        LiquorType liquorType = liquorTypeRepository
            .findByType(type)
            .orElseThrow(
                () -> new RuntimeException(NOT_LIQUOR_BREW_TYPE_FOUND.getMessage()));

        LiquorRegion liquorRegion = liquorRegionRepository
            .findByType(liquorRegionType)
            .orElseThrow(() -> new RuntimeException(NOT_LIQUOR_REGION_TYPE_FOUND.getMessage()));

        LiquorStatus liquorStatus = liquorStatusRepository
            .findByType(statusType)
            .orElseThrow(() -> new RuntimeException(NOT_LIQUOR_STATUS_TYPE_FOUND.getMessage()));

        Liquor liquor = Liquor.of(
            liquorType, liquorRegion,
            liquorStatus, request
        );
        return liquorRepository.save(liquor).getId();
    }
}
