package com.woowacamp.soolsool.core.liquor.service;

import com.woowacamp.soolsool.core.liquor.domain.LiquorCtr;
import com.woowacamp.soolsool.core.liquor.dto.liquorCtr.LiquorClickAddRequest;
import com.woowacamp.soolsool.core.liquor.dto.liquorCtr.LiquorImpressionAddRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorCtrRepository;
import com.woowacamp.soolsool.core.liquor.repository.redisson.LiquorCtrRedisRepository;
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

    public void increaseImpression(final LiquorImpressionAddRequest request) {
        request.getLiquorIds().forEach(liquorCtrRedisRepository::increaseImpression);
    }

    public void increaseClick(final LiquorClickAddRequest request) {
        liquorCtrRedisRepository.increaseClick(request.getLiquorId());
    }

    @Transactional
    public void writeBackCtr(final LiquorCtr latestLiquorCtr) {
        liquorCtrRepository.updateLiquorCtr(
            latestLiquorCtr.getImpression(),
            latestLiquorCtr.getClick(),
            latestLiquorCtr.getLiquorId()
        );
    }
}
