package com.woowacamp.soolsool.core.liquor.repository.redisson;

import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrImpression;
import com.woowacamp.soolsool.core.liquor.infra.RedisLiquorCtr;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import com.woowacamp.soolsool.global.infra.LockType;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LiquorCtrRedisRepository {

    private static final String LIQUOR_CTR_KEY = "LIQUOR_CTR";
    private static final long LOCK_WAIT_TIME = 3L;
    private static final long LOCK_LEASE_TIME = 3L;

    private final RedissonClient redissonClient;

    public void save(
        final Long liquorId,
        final LiquorCtrImpression impression,
        final LiquorCtrClick click
    ) {
        final RMapCache<Long, RedisLiquorCtr> liquorCtr =
            redissonClient.getMapCache(LIQUOR_CTR_KEY);

        liquorCtr.put(liquorId, new RedisLiquorCtr(impression.getImpression(), click.getClick()));
    }

    public LiquorCtrImpression findImpressionByLiquorId(final Long liquorId) {
        final RMapCache<Long, RedisLiquorCtr> liquorCtr =
            redissonClient.getMapCache(LIQUOR_CTR_KEY);

        validateExistsCtr(liquorId, liquorCtr);

        return new LiquorCtrImpression(liquorCtr.get(liquorId).getImpression());
    }

    public LiquorCtrClick findClickByLiquorId(final Long liquorId) {
        final RMapCache<Long, RedisLiquorCtr> liquorCtr =
            redissonClient.getMapCache(LIQUOR_CTR_KEY);

        validateExistsCtr(liquorId, liquorCtr);

        return new LiquorCtrClick(liquorCtr.get(liquorId).getClick());
    }

    public void increaseImpression(final Long liquorId) {
        final RLock rLock = redissonClient.getLock(LockType.LIQUOR_CTR.getPrefix() + liquorId);

        try {
            rLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            final RMapCache<Long, RedisLiquorCtr> liquorCtr =
                redissonClient.getMapCache(LIQUOR_CTR_KEY);

            validateExistsCtr(liquorId, liquorCtr);

            final RedisLiquorCtr redisLiquorCtr = liquorCtr.get(liquorId);

            liquorCtr.replace(liquorId, redisLiquorCtr.increaseImpression());
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new SoolSoolException(LiquorErrorCode.INTERRUPTED_THREAD);
        } finally {
            rLock.unlock();
        }
    }

    public void increaseClick(final Long liquorId) {
        final RLock rLock = redissonClient.getLock(LockType.LIQUOR_CTR.getPrefix() + liquorId);

        try {
            rLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            final RMapCache<Long, RedisLiquorCtr> liquorCtr =
                redissonClient.getMapCache(LIQUOR_CTR_KEY);

            validateExistsCtr(liquorId, liquorCtr);

            final RedisLiquorCtr redisLiquorCtr = liquorCtr.get(liquorId);

            liquorCtr.replace(liquorId, redisLiquorCtr.increaseClick());
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new SoolSoolException(LiquorErrorCode.INTERRUPTED_THREAD);
        } finally {
            rLock.unlock();
        }
    }

    private void validateExistsCtr(
        final Long liquorId,
        final RMapCache<Long, RedisLiquorCtr> liquorCtr
    ) {
        if (!liquorCtr.containsKey(liquorId)) {
            log.error("Redis에 LiquorId\"{}\"를 Key로 갖는 데이터가 존재하지 않습니다.", liquorId);

            throw new SoolSoolException(LiquorErrorCode.REDIS_HAS_NOT_CTR);
        }
    }
}
