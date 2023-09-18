package com.woowacamp.soolsool.core.liquor.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.config.RedisTestConfig;
import com.woowacamp.soolsool.core.liquor.dto.liquorCtr.LiquorClickAddRequest;
import com.woowacamp.soolsool.core.liquor.dto.liquorCtr.LiquorImpressionAddRequest;
import com.woowacamp.soolsool.core.liquor.infra.RedisLiquorCtr;
import com.woowacamp.soolsool.core.liquor.repository.redisson.LiquorCtrRedisRepository;
import com.woowacamp.soolsool.core.receipt.repository.redisson.ReceiptRedisRepository;
import java.util.List;
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
@Import({LiquorCtrService.class, RedisTestConfig.class, ReceiptRedisRepository.class,
    LiquorCtrRedisRepository.class})
@DisplayName("통합 테스트: LiquorCtrService")
class LiquorCtrServiceIntegrationTest {

    private static final String LIQUOR_CTR_KEY = "LIQUOR_CTR";

    @Autowired
    LiquorCtrService liquorCtrService;

    @Autowired
    RedissonClient redissonClient;

    @BeforeEach
    @AfterEach
    void setRedisLiquorCtr() {
        redissonClient.getMapCache(LIQUOR_CTR_KEY).clear();
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("클릭율을 조회한다.")
    void getLiquorCtrByByLiquorId() {
        // given
        long liquorId = 1L;

        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(liquorId, new RedisLiquorCtr(1L, 1L));

        // when
        double ctr = liquorCtrService.getLiquorCtrByLiquorId(liquorId);

        // then
        assertThat(ctr).isEqualTo(1.0);
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("노출수를 증가시킨다.")
    void increaseImpression() {
        // given
        long liquorId = 1L;
        LiquorImpressionAddRequest request = new LiquorImpressionAddRequest(List.of(liquorId));

        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(liquorId, new RedisLiquorCtr(1L, 1L));

        // when
        liquorCtrService.increaseImpression(request);

        // then
        double ctr = liquorCtrService.getLiquorCtrByLiquorId(liquorId);
        assertThat(ctr).isEqualTo(0.5);
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("클릭수를 증가시킨다.")
    void increaseClick() {
        // given
        long liquorId = 1L;
        LiquorClickAddRequest request = new LiquorClickAddRequest(liquorId);

        redissonClient.getMapCache(LIQUOR_CTR_KEY)
            .put(liquorId, new RedisLiquorCtr(2L, 1L));

        // when
        liquorCtrService.increaseClick(request);

        // then
        double ctr = liquorCtrService.getLiquorCtrByLiquorId(liquorId);
        assertThat(ctr).isEqualTo(1.0);
    }
}
