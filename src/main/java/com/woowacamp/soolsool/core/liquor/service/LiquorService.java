package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
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
    private final LiquorBrewRepository liquorBrewRepository;

    @Transactional
    public Long saveLiquor(final LiquorSaveRequest request) {
        final LiquorBrew liquorBrew = getBrew(request.getBrew());
        final LiquorRegion liquorRegion = getRegion(request.getRegion());
        final LiquorStatus liquorStatus = getStatus(request.getStatus());

        final Liquor liquor = request.toEntity(liquorBrew, liquorRegion, liquorStatus);
        
        return liquorRepository.save(liquor).getId();
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
