package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.config.RedisTestConfig;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorQueryDslRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusCache;
import com.woowacamp.soolsool.core.liquor.repository.redisson.LiquorCtrRedisRepository;
import com.woowacamp.soolsool.core.receipt.repository.redisson.ReceiptRedisRepository;
import com.woowacamp.soolsool.global.config.MultipleCacheManagerConfig;
import com.woowacamp.soolsool.global.config.QuerydslConfig;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import({LiquorService.class, LiquorBrewCache.class, LiquorStatusCache.class,
    LiquorRegionCache.class, LiquorQueryDslRepository.class,
    QuerydslConfig.class, MultipleCacheManagerConfig.class,
    RedisTestConfig.class, ReceiptRedisRepository.class, LiquorCtrRedisRepository.class})
@DisplayName("통합 테스트: LiquorService")
class LiquorServiceIntegrationTest {

    private static final String LIQUOR_CTR_KEY = "LIQUOR_CTR";

    @Autowired
    LiquorService liquorService;

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
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql",
        "/receipt-type.sql", "/receipt.sql",
        "/order-type.sql", "/order.sql"
    })
    @DisplayName("상품 상세 정보를 조회한다.")
    void liquorDetail() {
        /* given */
        Long 새로 = 1L;

        /* when */
        LiquorDetailResponse response = liquorService.liquorDetail(새로);

        /* then */
        assertAll(
            () -> assertThat(response.getId()).isEqualTo(1L),
            () -> assertThat(response.getName()).isEqualTo("새로"),
            () -> assertThat(response.getBrand()).isEqualTo("롯데"),
            () -> assertThat(response.getImageUrl()).isEqualTo("/soju-url"),
            () -> assertThat(response.getAlcohol()).isEqualTo(12.0),
            () -> assertThat(response.getVolume()).isEqualTo(300),
            () -> assertThat(response.getStock()).isEqualTo(100),
            () -> assertThat(response.getRelatedLiquors()).hasSize(1)
        );
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("상품 상세정보를 조회할 경우 클릭율을 증가시킨다.")
    void increaseClick() {
        // given
        long liquorId = 1L;

        // when
        liquorService.liquorDetail(liquorId);

        // then
        double ctr = liquorCtrRedisRepository.getCtr(liquorId);
        assertThat(ctr).isEqualTo(1.0);
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("상품 목록을 조회할 경우 클릭율을 증가시킨다.")
    void increaseImpression() {
        // given
        long liquorId = 1L;

        // when
        liquorService.liquorList(null, null, null, null,
            PageRequest.of(0, 1), liquorId + 1);

        // then
        double ctr = liquorCtrRedisRepository.getCtr(liquorId);
        assertThat(ctr).isEqualTo(0.33);
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql"
    })
    @DisplayName("liquor를 저장한다.")
    void saveLiquorTest() {
        // given
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "새로", "3000", "브랜드", "/url",
            12.0, 300);

        // when & then
        assertThatCode(() -> liquorService.saveLiquor(liquorSaveRequest))
            .doesNotThrowAnyException();
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"
    })
    @DisplayName("liquor를 수정한다.")
    void modifyLiquorTest() {
        // given
        LiquorDetailResponse target = liquorService.liquorDetail(1L);
        LiquorModifyRequest liquorModifyRequest = new LiquorModifyRequest(
            "BERRY", "GYEONGGI_DO", "ON_SALE",
            "새로2", "3000", "브랜드", "/url",
            100, 12.0, 300,
            LocalDateTime.now().plusYears(10L)
        );

        // when
        liquorService.modifyLiquor(target.getId(), liquorModifyRequest);

        // then
        LiquorDetailResponse liquor = liquorService.liquorDetail(1L);

        assertThat(liquor.getName()).isEqualTo(liquorModifyRequest.getName());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql"
    })
    @DisplayName("liquor Id가 존재하지 않을 때, 수정 시 에러를 반환한다.")
    void modifyLiquorTestFailWithNoExistId() {
        // given
        Long liquorId = 999L;
        LiquorModifyRequest liquorModifyRequest = new LiquorModifyRequest(
            "BERRY", "GYEONGGI_DO", "ON_SALE",
            "새로2", "3000", "브랜드", "/url",
            100, 12.0, 300,
            LocalDateTime.now().plusYears(10L)
        );

        // when & then
        assertThatCode(() -> liquorService.modifyLiquor(liquorId, liquorModifyRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_LIQUOR_FOUND.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql"
    })
    @DisplayName("liquor를 삭제한다.")
    void deleteLiquorTest() {
        // given

        // when
        liquorService.deleteLiquor(1L);

        // then
        assertThatCode(() -> liquorService.liquorDetail(1L))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("술이 존재하지 않습니다.");
    }
}
