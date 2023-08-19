package com.woowacamp.soolsool.core.cart.domain.vo;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("장바구니 상품 수량 단위 테스트")
class CartItemQuantityTest {

    @Test
    @DisplayName("장바구니 상품 수량을 정상적으로 생성한다.")
    void create() {
        /* given */
        final int quantity = 100;

        /* when & then */
        assertThatCode(() -> new CartItemQuantity(quantity))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("장바구니 상품 수량이 1 미만일 경우 SoolSoolException을 던진다.")
    void createFailWithLessThanOne(int quantity) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new CartItemQuantity(quantity))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("장바구니 상품 수량은 1개 이상이어야 합니다.");
    }
}
