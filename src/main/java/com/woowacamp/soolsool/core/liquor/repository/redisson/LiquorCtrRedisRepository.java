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

    // TODO: RedisLiquorCtr vs LiquorCtr -> "Best practice(Jpa + Redis)"을 참고하는게 맞을 것 같다.
    // TODO: LiquorCtrRedisService vs LiquorCtrRedisRepository -> 취향 차이다...
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

    // ?
    // 여기 repository인데 비즈니스 로직을 하는게 맞나...?
    // 사실 repository면 getCtr이 아니라 getLiquorCtr을 public으로 제공해야하는거 아닌가?

    // Repository -> storage value -> memory
    // lockUP 있는게 자연스럽다...
    // write ... -> writeListener
    // write가 있는 애...

    // LCRR -> Service

    // 1. LCRR의 로직을 LCS 해야하는거 아닌가?
    // 2. RedissonClient 감싸는 Repository를 만들어서 LCS에서 남은 로직을 마무리한다.
    // RedissonClient -> Repository
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

    // JpaRepository.updateAgeOne
    // RedisRepository.updateImpressionOne

    // 비관전락 했을 때 LiquorCtrRepsotiory.findById() <- @Lock
    // Repository에서 락을 건게 아닌가?
    // RLock을 Redis에서 빌린다고 생각 -> redissonClient.getLock() 자체가 Redis에 락 요청 보내는거아냐?

    // AOP 구현했을 때 -> @Transactional -> repository에도 붙인다.
    // Service 메서드에서 트랜잭션 시작을 원치 않을 수도 있다
    // service.method( repo.m1, repo.m2 )
    // repository에서 @RedisLock 을 붙이는게 자연스럽다...?
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

    // TODO: 만료 테스트는 어떻게 해야할까?
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
