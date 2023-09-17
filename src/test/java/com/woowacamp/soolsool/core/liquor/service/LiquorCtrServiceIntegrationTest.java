package com.woowacamp.soolsool.core.liquor.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.config.RedisTestConfig;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorQueryDslRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusCache;
import com.woowacamp.soolsool.core.liquor.repository.redisson.LiquorCtrRedisRepository;
import com.woowacamp.soolsool.core.receipt.repository.redisson.ReceiptRedisRepository;
import com.woowacamp.soolsool.global.config.MultipleCacheManagerConfig;
import com.woowacamp.soolsool.global.config.QuerydslConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import({LiquorService.class, LiquorBrewCache.class, LiquorStatusCache.class,
    LiquorRegionCache.class, LiquorQueryDslRepository.class,
    QuerydslConfig.class, MultipleCacheManagerConfig.class,
    RedisTestConfig.class, ReceiptRedisRepository.class, LiquorCtrRedisRepository.class})
@DisplayName("통합 테스트: LiquorService")
class LiquorCtrServiceIntegrationTest {

    private static final String LIQUOR_CTR_KEY = "LIQUOR_CTR";

    @Autowired
    LiquorCtrRedisRepository liquorCtrRedisRepository;

    @Autowired
    RedissonClient redissonClient;

    @BeforeEach
    @AfterEach
    void setRedisLiquorCtr() {
        redissonClient.getMapCache(LIQUOR_CTR_KEY).clear();
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("노출수를 증가시킨다.")
    void increaseImpression() {
        // given
        long liquorId = 1L;

        // when
        liquorCtrRedisRepository.increaseImpression(liquorId);

        // then
        double ctr = liquorCtrRedisRepository.getCtr(liquorId);
        assertThat(ctr).isEqualTo(0.33);
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("클릭수를 증가시킨다.")
    void increaseClick() {
        // given
        long liquorId = 1L;

        // when
        liquorCtrRedisRepository.increaseClick(liquorId);

        // then
        double ctr = liquorCtrRedisRepository.getCtr(liquorId);
        assertThat(ctr).isEqualTo(1.0);
    }
}
