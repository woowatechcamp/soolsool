package com.woowacamp.soolsool.core.statistics.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("단위 테스트: Statistic")
class StatisticTest {

    @Test
    @DisplayName("객체 생성 성공")
    void create() {
        // when & then
        assertThatNoException()
                .isThrownBy(() -> Statistic.builder()
                        .salePrice(new BigInteger("10000"))
                        .saleQuantity(new BigInteger("100"))
                        .impression(new BigInteger("1000"))
                        .region("서울")
                        .brewType("과일주")
                        .click(new BigInteger("120"))
                        .build());
    }
}
