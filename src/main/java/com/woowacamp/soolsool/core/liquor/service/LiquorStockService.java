package com.woowacamp.soolsool.core.liquor.service;

import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.dto.LiquorStockSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStockRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LiquorStockService {

    private final LiquorRepository liquorRepository;
    private final LiquorStockRepository liquorStockRepository;

    public Long saveLiquorStock(final LiquorStockSaveRequest request) {
        final Liquor liquor = liquorRepository.findById(request.getLiquorId())
            .orElseThrow(() -> new SoolSoolException(LiquorErrorCode.NOT_LIQUOR_FOUND));

        return liquorStockRepository.save(request.toEntity(liquor)).getId();
    }
}
