package com.woowacamp.soolsool.core.liquor.repository.redisson;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrImpression;
import com.woowacamp.soolsool.core.liquor.infra.RedisLiquorCtr;
import com.woowacamp.soolsool.core.liquor.repository.LiquorCtrRepository;
import com.woowacamp.soolsool.global.config.RedissonConfig;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import({LiquorCtrRedisRepository.class, RedissonConfig.class})
@DisplayName("통합 테스트 : LiquorCtrRedisRepository")
class LiquorCtrRedisRepositoryTest {

    private static final String LIQUOR_CTR_KEY = "LIQUOR_CTR";

    @Autowired
    LiquorCtrRedisRepository liquorCtrRedisRepository;

    @Autowired
    LiquorCtrRepository liquorCtrRepository;

    @Autowired
    RedissonClient redissonClient;

    Long liquorId = 1L;

    @BeforeEach
    @AfterEach
    void setUpLiquorCtr() {
        RMapCache<Long, RedisLiquorCtr> mapCache = redissonClient.getMapCache(LIQUOR_CTR_KEY);

        mapCache.clear();
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("클릭율을 조회한다.")
    void getCtr() {
        // given
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(liquorId, new RedisLiquorCtr(2L, 1L));

        // when
        double ctr = liquorCtrRedisRepository.getCtr(liquorId);

        // then
        assertThat(ctr).isEqualTo(0.5);
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("Redis에 Ctr 정보가 없을 경우 DB에 저장된 Ctr을 조회한다.")
    void getCtrIfAbsent() {
        // given

        // when
        double ctr = liquorCtrRedisRepository.getCtr(liquorId);

        // then
        assertThat(ctr).isEqualTo(0.5);
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("Redis에 Ctr 정보가 존재하지 않을 경우 DB를 조회해 Redis에 반영한다.")
    void synchronizeWithDatabase() {
        // given
        Long expected = liquorCtrRepository.findByLiquorId(liquorId)
            .orElseThrow(() -> new RuntimeException("LiquorCtr이 존재하지 않습니다."))
            .getImpression();

        // when
        long result = liquorCtrRedisRepository.getImpressionByLiquorId(liquorId).getImpression();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("노출수를 1 증가시킨다.")
    void updateImpression() {
        // given
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(liquorId, new RedisLiquorCtr(0L, 0L));

        // when
        liquorCtrRedisRepository.increaseImpression(liquorId);

        // then
        LiquorCtrImpression click = liquorCtrRedisRepository.getImpressionByLiquorId(liquorId);
        assertThat(click.getImpression()).isEqualTo(1L);
    }

    @Test
    @DisplayName("멀티 쓰레드를 사용해 노출수를 50 증가시킨다.")
    void updateImpressionByMultiThread() throws InterruptedException {
        // given
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(liquorId, new RedisLiquorCtr(0L, 0L));

        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                liquorCtrRedisRepository.increaseImpression(liquorId);
                latch.countDown();
            });
        }
        latch.await();

        // then
        LiquorCtrImpression click = liquorCtrRedisRepository.getImpressionByLiquorId(liquorId);
        assertThat(click.getImpression()).isEqualTo(threadCount);
    }

    @Test
    @DisplayName("클릭수를 1 증가시킨다.")
    void updateClick() {
        // given
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(liquorId, new RedisLiquorCtr(0L, 0L));

        // when
        liquorCtrRedisRepository.increaseClick(liquorId);

        // then
        LiquorCtrClick click = liquorCtrRedisRepository.getClickByLiquorId(liquorId);
        assertThat(click.getClick()).isEqualTo(1);
    }

    @Test
    @DisplayName("멀티 쓰레드를 사용해 클릭수를 50 증가시킨다.")
    void updateClickByMultiThread() throws InterruptedException {
        // given
        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(liquorId, new RedisLiquorCtr(0L, 0L));

        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                liquorCtrRedisRepository.increaseClick(liquorId);
                latch.countDown();
            });
        }
        latch.await();

        // then
        LiquorCtrClick click = liquorCtrRedisRepository.getClickByLiquorId(liquorId);
        assertThat(click.getClick()).isEqualTo(threadCount);
    }
}
