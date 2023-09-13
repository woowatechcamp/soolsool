package com.woowacamp.soolsool.core.liquor.service;

import com.woowacamp.soolsool.core.liquor.code.LiquorCtrErrorCode;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrImpression;
import com.woowacamp.soolsool.core.liquor.repository.LiquorCtrRepository;
import com.woowacamp.soolsool.core.liquor.repository.redisson.LiquorCtrRedisRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LiquorCtrService {

    private final LiquorCtrRepository liquorCtrRepository;

    private final LiquorCtrRedisRepository liquorCtrRedisRepository;

    @Transactional(readOnly = true)
    public double getLiquorCtrByLiquorId(final Long liquorId) {
        return liquorCtrRedisRepository.getCtr(liquorId);
    }

    @Transactional
    public void writeBackCtr(
        final Long liquorId,
        final LiquorCtrImpression impression,
        final LiquorCtrClick click
    ) {
        liquorCtrRepository.findByLiquorId(liquorId)
            .orElseThrow(() -> new SoolSoolException(LiquorCtrErrorCode.NOT_LIQUOR_CTR_FOUND))
            .updateCtr(impression, click);
    }
}
