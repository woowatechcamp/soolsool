package com.woowacamp.soolsool.core.liquor.service;

import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.core.liquor.domain.LiquorCtr;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrImpression;
import com.woowacamp.soolsool.core.liquor.repository.LiquorCtrRepository;
import com.woowacamp.soolsool.core.liquor.repository.redisson.LiquorCtrRedisRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import com.woowacamp.soolsool.global.infra.LockType;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LiquorCtrService {

    private static final long LOCK_WAIT_TIME = 3L;
    private static final long LOCK_LEASE_TIME = 3L;

    private final LiquorCtrRepository liquorCtrRepository;

    private final LiquorCtrRedisRepository liquorCtrRedisRepository;

    private final RedissonClient redissonClient;

    @Transactional(readOnly = true)
    public double getLiquorCtrByLiquorId(final Long liquorId) {
        return liquorCtrRedisRepository.getCtr(liquorId);
    }

    @Transactional
    public void writeBackLiquorCtr(
        final Long liquorId,
        final LiquorCtrImpression impression,
        final LiquorCtrClick click
    ) {
        final RLock rLock = redissonClient.getLock(LockType.LIQUOR_CTR.getPrefix() + liquorId);

        try {
            rLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            final LiquorCtr liquorCtr = liquorCtrRepository.findByLiquorId(liquorId)
                .orElseThrow(() -> new SoolSoolException(LiquorErrorCode.NOT_LIQUOR_CTR_FOUND));

            liquorCtr.saveImpression(impression);
            liquorCtr.saveClick(click);

            liquorCtrRedisRepository.synchronizedWithDatabase(liquorId, impression, click);
        } catch (final InterruptedException e) {
            log.error("Redis to MySQL, LiquorCtr 갱신에 실패했습니다. | liquorId : {}", liquorId);

            Thread.currentThread().interrupt();

            throw new SoolSoolException(LiquorErrorCode.INTERRUPTED_THREAD);
        } finally {
            rLock.unlock();
        }
    }
}
