package com.woowacamp.soolsool.core.liquor.repository.redisson;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrImpression;
import com.woowacamp.soolsool.global.config.RedissonConfig;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({LiquorCtrRedisRepository.class, RedissonConfig.class})
@DisplayName("통합 테스트 : LiquorCtrRedisRepository")
class LiquorCtrRedisRepositoryTest {

    @Autowired
    LiquorCtrRedisRepository liquorCtrRedisRepository;

    @Test
    @DisplayName("노출수를 1 증가시킨다.")
    void updateImpression() {
        // given
        liquorCtrRedisRepository.save(1L, new LiquorCtrImpression(0L), new LiquorCtrClick(0L));

        // when
        liquorCtrRedisRepository.increaseImpression(1L);

        // then
        LiquorCtrImpression click = liquorCtrRedisRepository.findImpressionByLiquorId(1L);
        assertThat(click.getImpression()).isEqualTo(1L);
    }

    @Test
    @DisplayName("다중 쓰레드를 사용해 노출수를 50 증가시킨다.")
    void updateImpressionByMultiThread() throws InterruptedException {
        // given
        long liquorId = 1L;
        liquorCtrRedisRepository.save(liquorId, new LiquorCtrImpression(0L), new LiquorCtrClick(0L));

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
        LiquorCtrImpression click = liquorCtrRedisRepository.findImpressionByLiquorId(liquorId);
        assertThat(click.getImpression()).isEqualTo(threadCount);
    }

    @Test
    @DisplayName("클릭수를 1 증가시킨다.")
    void updateClick() {
        // given
        liquorCtrRedisRepository.save(1L, new LiquorCtrImpression(0L), new LiquorCtrClick(0L));

        // when
        liquorCtrRedisRepository.increaseClick(1L);

        // then
        LiquorCtrClick click = liquorCtrRedisRepository.findClickByLiquorId(1L);
        assertThat(click.getClick()).isEqualTo(1L);
    }

    @Test
    @DisplayName("다중 쓰레드를 사용해 클릭수를 50 증가시킨다.")
    void updateClickByMultiThread() throws InterruptedException {
        // given
        long liquorId = 1L;
        liquorCtrRedisRepository.save(liquorId, new LiquorCtrImpression(0L), new LiquorCtrClick(0L));

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
        LiquorCtrClick click = liquorCtrRedisRepository.findClickByLiquorId(liquorId);
        assertThat(click.getClick()).isEqualTo(threadCount);
    }
}
