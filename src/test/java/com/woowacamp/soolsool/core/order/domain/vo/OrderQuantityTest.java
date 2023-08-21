package com.woowacamp.soolsool.core.order.domain.vo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("주문 수량 단위 테스트")
class OrderQuantityTest {

    @Test
    @DisplayName("주문 수량을 정상적으로 생성한다.")
    void createOrderQuantity() {
        // given
        int quantity = 1;

        // when & then
        assertDoesNotThrow(() -> new OrderQuantity(quantity));
    }

    @Test
    @DisplayName("주문 수량이 null일 경우 SoolSoolException을 던진다.")
    void createFailWithNull() {
        // given


        // when & then
        assertThrows(SoolSoolException.class, () -> new OrderQuantity(null));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("주문 수량이 양수가 아닐 경우 SoolSoolException을 던진다.")
    void createFailWithNotPositive(int quantity) {
        // given


        // when & then
        assertThrows(SoolSoolException.class, () -> new OrderQuantity(quantity));
    }
}
