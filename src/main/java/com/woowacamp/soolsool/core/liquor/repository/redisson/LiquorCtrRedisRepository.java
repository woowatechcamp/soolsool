package com.woowacamp.soolsool.core.liquor.repository.redisson;

import com.woowacamp.soolsool.core.liquor.code.LiquorCtrErrorCode;
import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrImpression;
import com.woowacamp.soolsool.core.liquor.event.LiquorCtrExpiredEvent;
import com.woowacamp.soolsool.core.liquor.infra.RedisLiquorCtr;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import com.woowacamp.soolsool.global.infra.LockType;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LiquorCtrRedisRepository {

    private static final String LIQUOR_CTR_KEY = "LIQUOR_CTR";
    private static final long LOCK_WAIT_TIME = 3L;
    private static final long LOCK_LEASE_TIME = 3L;
    private static final long LIQUOR_CTR_TTL = 5L;

    private final RedissonClient redissonClient;

    public LiquorCtrRedisRepository(
        final RedissonClient redissonClient,
        final ApplicationEventPublisher publisher
    ) {
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .addListener((EntryExpiredListener<Long, RedisLiquorCtr>) event ->
                publisher.publishEvent(new LiquorCtrExpiredEvent(
                    event.getKey(),
                    new LiquorCtrImpression(event.getValue().getImpression()),
                    new LiquorCtrClick(event.getValue().getClick()))
                )
            );

        this.redissonClient = redissonClient;
    }

    public double getCtr(final Long liquorId) {
        final RMapCache<Long, RedisLiquorCtr> liquorCtr =
            redissonClient.getMapCache(LIQUOR_CTR_KEY);

        validateExistsCtr(liquorId, liquorCtr);

        final Long click = liquorCtr.get(liquorId).getClick();
        final Long impression = liquorCtr.get(liquorId).getImpression();

        if (impression == 0) {
            throw new SoolSoolException(LiquorCtrErrorCode.DIVIDE_BY_ZERO_IMPRESSION);
        }

        final double ratio = (double) click / impression;

        return Math.round(ratio * 100) / 100.0;
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

            initLiquorCtrIfAbsent(liquorCtr, liquorId);

            final RedisLiquorCtr redisLiquorCtr = liquorCtr.get(liquorId);
            liquorCtr.replace(liquorId, redisLiquorCtr.increaseImpression());
        } catch (final InterruptedException e) {
            log.error("노출수 갱신에 실패했습니다. | liquorId : {}", liquorId);

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

            initLiquorCtrIfAbsent(liquorCtr, liquorId);

            final RedisLiquorCtr redisLiquorCtr = liquorCtr.get(liquorId);

            liquorCtr.replace(liquorId, redisLiquorCtr.increaseClick());
        } catch (final InterruptedException e) {
            log.error("클릭수 갱신에 실패했습니다. | liquorId : {}", liquorId);

            Thread.currentThread().interrupt();

            throw new SoolSoolException(LiquorErrorCode.INTERRUPTED_THREAD);
        } finally {
            rLock.unlock();
        }
    }

    public void synchronizedWithDatabase(
        final Long liquorId,
        final LiquorCtrImpression impression,
        final LiquorCtrClick click
    ) {
        final RMapCache<Long, RedisLiquorCtr> liquorCtr =
            redissonClient.getMapCache(LIQUOR_CTR_KEY);

        final RedisLiquorCtr synchronizedLiquorCtr = liquorCtr.getOrDefault(liquorId, new RedisLiquorCtr(0L, 0L))
            .synchronizedWithDatabase(impression.getImpression(), click.getCount());

        if (liquorCtr.containsKey(liquorId)) {
            liquorCtr.replace(liquorId, synchronizedLiquorCtr);
        } else {
            liquorCtr.put(liquorId, synchronizedLiquorCtr, LIQUOR_CTR_TTL, TimeUnit.MINUTES);
        }
    }

    private void initLiquorCtrIfAbsent(
        final RMapCache<Long, RedisLiquorCtr> liquorCtr,
        final Long liquorId
    ) {
        if (liquorCtr.get(liquorId) != null) {
            return;
        }
        liquorCtr.put(
            liquorId,
            new RedisLiquorCtr(0L, 0L),
            LIQUOR_CTR_TTL,
            TimeUnit.MINUTES
        );
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
