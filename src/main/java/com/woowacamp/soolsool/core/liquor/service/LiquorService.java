package com.woowacamp.soolsool.core.liquor.service;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
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
}
