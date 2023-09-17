package com.woowacamp.soolsool.core.statistics.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;

import com.woowacamp.soolsool.core.statistics.domain.vo.BrewType;
import com.woowacamp.soolsool.core.statistics.domain.vo.Click;
import com.woowacamp.soolsool.core.statistics.domain.vo.Impression;
import com.woowacamp.soolsool.core.statistics.domain.vo.Region;
import com.woowacamp.soolsool.core.statistics.domain.vo.SalePrice;
import com.woowacamp.soolsool.core.statistics.domain.vo.SaleQuantity;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: Statistic")
class StatisticTest {

    @Test
    @DisplayName("객체 생성 성공")
    void create() {
        // when & then
        assertThatNoException()
            .isThrownBy(() -> Statistic.builder()
                .salePrice(new SalePrice(new BigInteger("10000")))
                .saleQuantity(new SaleQuantity(new BigInteger("100")))
                .impression(new Impression(new BigInteger("1000")))
                .region(new Region("서울"))
                .brewType(new BrewType("과일주"))
                .click(new Click(new BigInteger("120")))
                .build());
    }
}
