package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
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
    public Long saveLiquor(final LiquorSaveRequest request) {
        LiquorType liquorType = liquorTypeRepository
            .findByType(LiquorBrewType.valueOf(request.getTypeName()));
        LiquorRegion liquorRegion = liquorRegionRepository
            .findByType(LiquorRegionType.valueOf(request.getRegionName()));
        LiquorStatus liquorStatus = liquorStatusRepository
            .findByType(LiquorStatusType.valueOf(request.getStatusName()));

        Liquor liquor = request.toEntity(liquorType, liquorRegion, liquorStatus);
        return liquorRepository.save(liquor).getId();
    }

    @Transactional
    public void modifyLiquor(Long liquorId, LiquorModifyRequest liquorModifyRequest) {
        Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));

        LiquorType modifyLiquorType = liquorTypeRepository
            .findByType(LiquorBrewType.valueOf(liquorModifyRequest.getTypeName()));
        LiquorRegion modifyLiquorRegion = liquorRegionRepository
            .findByType(LiquorRegionType.valueOf(liquorModifyRequest.getRegionName()));
        LiquorStatus modifyLiquorStatus = liquorStatusRepository
            .findByType(LiquorStatusType.valueOf(liquorModifyRequest.getStatusName()));

        liquor.update(
            modifyLiquorType, modifyLiquorRegion,
            modifyLiquorStatus, liquorModifyRequest
        );
    }


}
