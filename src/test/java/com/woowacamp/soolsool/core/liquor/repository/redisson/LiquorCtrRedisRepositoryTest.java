package com.woowacamp.soolsool.core.liquor.repository.redisson;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.config.RedisTestConfig;
import com.woowacamp.soolsool.core.liquor.infra.RedisLiquorCtr;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import({LiquorCtrRedisRepository.class, RedisTestConfig.class})
@DisplayName("통합 테스트 : LiquorCtrRedisRepository")
class LiquorCtrRedisRepositoryTest {

    private static final String LIQUOR_CTR_KEY = "LIQUOR_CTR";
    private static final Long TARGET_LIQUOR = 1L;

    @Autowired
    LiquorCtrRedisRepository liquorCtrRedisRepository;

    @Autowired
    RedissonClient redissonClient;

    @AfterEach
    void setOffLiquorCtr() {
        redissonClient.getMapCache(LIQUOR_CTR_KEY).clear();
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("클릭율을 조회한다.")
    void getCtr() {
        // given
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(TARGET_LIQUOR, new RedisLiquorCtr(2L, 1L));

        // when
        double ctr = liquorCtrRedisRepository.getCtr(TARGET_LIQUOR);

        // then
        assertThat(ctr).isEqualTo(0.5);
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("Redis에 Ctr 정보가 존재하지 않을 경우 DB를 조회해 Redis에 반영한다.")
    void synchronizeWithDatabase() {
        // given

        // when
        double ctr = liquorCtrRedisRepository.getCtr(TARGET_LIQUOR);

        // then
        assertThat(ctr).isEqualTo(0.5);
    }

    @Test
    @DisplayName("노출수를 1 증가시킨다.")
    void updateImpression() {
        // given
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(TARGET_LIQUOR, new RedisLiquorCtr(1L, 1L));

        // when
        liquorCtrRedisRepository.increaseImpression(TARGET_LIQUOR);

        // then
        double ctr = liquorCtrRedisRepository.getCtr(TARGET_LIQUOR);
        assertThat(ctr).isEqualTo(0.5);
    }

    @Test
    @DisplayName("멀티 쓰레드를 사용해 노출수를 50 증가시킨다.")
    void updateImpressionByMultiThread() throws InterruptedException {
        // given
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(TARGET_LIQUOR, new RedisLiquorCtr(50L, 50L));

        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                liquorCtrRedisRepository.increaseImpression(TARGET_LIQUOR);
                latch.countDown();
            });
        }
        latch.await();

        // then
        double ctr = liquorCtrRedisRepository.getCtr(TARGET_LIQUOR);
        assertThat(ctr).isEqualTo(0.5);
    }

    @Test
    @DisplayName("클릭수를 1 증가시킨다.")
    void updateClick() {
        // given
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(TARGET_LIQUOR, new RedisLiquorCtr(1L, 0L));

        // when
        liquorCtrRedisRepository.increaseClick(TARGET_LIQUOR);

        // then
        double ctr = liquorCtrRedisRepository.getCtr(TARGET_LIQUOR);
        assertThat(ctr).isEqualTo(1);
    }

    @Test
    @DisplayName("멀티 쓰레드를 사용해 클릭수를 50 증가시킨다.")
    void updateClickByMultiThread() throws InterruptedException {
        // given
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(TARGET_LIQUOR, new RedisLiquorCtr(50L, 0L));

        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                liquorCtrRedisRepository.increaseClick(TARGET_LIQUOR);
                latch.countDown();
            });
        }
        latch.await();

        // then
        double ctr = liquorCtrRedisRepository.getCtr(TARGET_LIQUOR);
        assertThat(ctr).isEqualTo(1);
    }
}
