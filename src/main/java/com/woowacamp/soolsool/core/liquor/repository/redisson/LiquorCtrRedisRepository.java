package com.woowacamp.soolsool.core.liquor.repository.redisson;

import com.woowacamp.soolsool.core.liquor.code.LiquorCtrErrorCode;
import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.core.liquor.domain.LiquorCtr;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrImpression;
import com.woowacamp.soolsool.core.liquor.event.LiquorCtrExpiredEvent;
import com.woowacamp.soolsool.core.liquor.infra.RedisLiquorCtr;
import com.woowacamp.soolsool.core.liquor.repository.LiquorCtrRepository;
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

    private final LiquorCtrRepository liquorCtrRepository;

    private final RedissonClient redissonClient;

    // TODO: RedisLiquorCtr vs LiquorCtr
    public LiquorCtrRedisRepository(
        final LiquorCtrRepository liquorCtrRepository,
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

        this.liquorCtrRepository = liquorCtrRepository;
        this.redissonClient = redissonClient;
    }

    public double getCtr(final Long liquorId) {
        final RedisLiquorCtr liquorCtr = lookUpLiquorCtr(liquorId);
        final Long click = liquorCtr.getClick();
        final Long impression = liquorCtr.getImpression();

        validateDividedByZero(impression);

        final double ratio = (double) click / impression;

        return Math.round(ratio * 100) / 100.0;
    }

    private void validateDividedByZero(final Long impression) {
        if (impression == 0) {
            throw new SoolSoolException(LiquorCtrErrorCode.DIVIDE_BY_ZERO_IMPRESSION);
        }
    }

    public void increaseImpression(final Long liquorId) {
        final RLock rLock = redissonClient.getLock(LockType.LIQUOR_CTR.getPrefix() + liquorId);

        try {
            rLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            replaceLiquorCtr(liquorId, lookUpLiquorCtr(liquorId).increaseImpression());
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

            replaceLiquorCtr(liquorId, lookUpLiquorCtr(liquorId).increaseClick());
        } catch (final InterruptedException e) {
            log.error("클릭수 갱신에 실패했습니다. | liquorId : {}", liquorId);

            Thread.currentThread().interrupt();

            throw new SoolSoolException(LiquorErrorCode.INTERRUPTED_THREAD);
        } finally {
            rLock.unlock();
        }
    }

    private RedisLiquorCtr lookUpLiquorCtr(final Long liquorId) {
        final RMapCache<Long, RedisLiquorCtr> liquorCtrs =
            redissonClient.getMapCache(LIQUOR_CTR_KEY);

        if (!liquorCtrs.containsKey(liquorId)) {
            final LiquorCtr liquorCtr = liquorCtrRepository.findByLiquorId(liquorId)
                .orElseThrow(() -> new SoolSoolException(LiquorCtrErrorCode.NOT_LIQUOR_CTR_FOUND));

            liquorCtrs.put(
                liquorId,
                new RedisLiquorCtr(liquorCtr.getImpression(), liquorCtr.getClick()),
                LIQUOR_CTR_TTL,
                TimeUnit.MINUTES
            );
        }

        return liquorCtrs.get(liquorId);
    }

    private void replaceLiquorCtr(final Long liquorId, final RedisLiquorCtr redisLiquorCtr) {
        final RMapCache<Long, RedisLiquorCtr> liquorCtrs =
            redissonClient.getMapCache(LIQUOR_CTR_KEY);

        liquorCtrs.replace(liquorId, redisLiquorCtr);
    }
}
