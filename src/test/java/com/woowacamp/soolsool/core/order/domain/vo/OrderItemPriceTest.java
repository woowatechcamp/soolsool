package com.woowacamp.soolsool.core.order.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacamp.soolsool.global.exception.ShoppingException;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("주문 상품 가격 단위 테스트")
class OrderItemPriceTest {

    @Test
    @DisplayName("주문 상품 가격을 정상적으로 생성한다.")
    void create() {
        // given
        BigInteger price = BigInteger.valueOf(10_000L);

        // when & then
        assertDoesNotThrow(() -> new OrderItemPrice(price));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0"})
    @DisplayName("주문 상품 가격이 0 이하일 경우 ShoppingException을 던진다.")
    void createFailWithInvalidPrice(String invalidPrice) {
        // given
        BigInteger price = new BigInteger(invalidPrice);

        // when & then
        assertThatThrownBy(() -> new OrderItemPrice(price))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("주문 상품 가격은 0 이하일 수 없습니다.");
    }

    @Test
    @DisplayName("주문 상품 가격이 null일 경우 ShoppingException을 던진다.")
    void createFailWithNull() {
        // given


        // when & then
        assertThatThrownBy(() -> new OrderItemPrice(null))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("주문 상품 가격은 null일 수 없습니다.");
    }

    @Test
    @DisplayName("주문 상품 가격이 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        // given
        OrderItemPrice origin = new OrderItemPrice(BigInteger.ONE);
        OrderItemPrice same = new OrderItemPrice(BigInteger.ONE);
        OrderItemPrice different = new OrderItemPrice(BigInteger.TEN);

        // when & then
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}

