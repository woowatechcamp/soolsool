package com.woowacamp.soolsool.integration.service;


import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(CartService.class)
@DisplayName("CartItemService 테스트")
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("장바구니 상품으로 존재하지 않는 술을 추가할 경우 예외를 던진다.")
    void saveNotExistsLiquorByCartItem() {
        // given
        Long memberId = 1L;
        CartItemSaveRequest request = new CartItemSaveRequest(99999L, 1);

        // when & then
        Assertions.assertThrows(SoolSoolException.class,
            () -> cartService.saveCartItem(memberId, request));
    }
}
