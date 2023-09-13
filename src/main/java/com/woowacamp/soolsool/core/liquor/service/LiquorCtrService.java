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
        // JPA -> find -> memory update -> dirty check
        liquorCtrRepository.findByLiquorId(liquorId)
            .orElseThrow(() -> new SoolSoolException(LiquorCtrErrorCode.NOT_LIQUOR_CTR_FOUND))
            .updateCtr(impression, click);

        // update query -> commit
//        liquorCtrRepository.updateCtr(liquorId, impression, click); // 없어도 업데이트 하고 있어도 업데이트 한다
        // 없는데 새로 넣는 경우가 문제자잖아
        // 없으면 없는놈을 업데이트해서 어짜피 새로 생길일이 없다.

        // RedisLiquorCtr vs LiquorCtr -> RedisLiquorCtr win

        // RedisLiquorCtr가 있으면 LiquorCtr과 RLC 모두 getCtr()을 가지고 있어야한다.
    }
}
