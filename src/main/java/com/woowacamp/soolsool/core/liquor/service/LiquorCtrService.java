package com.woowacamp.soolsool.core.liquor.service;

import com.woowacamp.soolsool.core.liquor.code.LiquorCtrErrorCode;
import com.woowacamp.soolsool.core.liquor.repository.LiquorCtrRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LiquorCtrService {

    private final LiquorCtrRepository liquorCtrRepository;

    @Transactional(readOnly = true)
    public double getLiquorCtrByLiquorId(final Long liquorId) {
        return liquorCtrRepository.findByLiquorId(liquorId)
            .orElseThrow(() -> new SoolSoolException(LiquorCtrErrorCode.NOT_LIQUOR_CTR_FOUND))
            .getCtr();
    }
}
