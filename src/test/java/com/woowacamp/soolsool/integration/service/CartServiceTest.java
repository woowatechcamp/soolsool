package com.woowacamp.soolsool.integration.service;


import static com.woowacamp.soolsool.core.cart.domain.code.CartErrorCode.INVALID_QUANTITY_SIZE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.woowacamp.soolsool.core.cart.dto.request.CartItemModifyRequest;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({CartService.class, LiquorService.class})
@DisplayName("CartItemService 테스트")
class CartServiceTest {

    @Autowired
    private CartService cartService;
    @Autowired
    private LiquorService liquorService;


    @Test
    @DisplayName("장바구니 상품으로 존재하지 않는 술을 추가할 경우 예외를 던진다.")
    void saveNotExistsLiquorByCartItem() {
        // given
        Long memberId = 1L;
        CartItemSaveRequest request = new CartItemSaveRequest(99999L, 1);

        // when & then
        assertThrows(SoolSoolException.class,
            () -> cartService.saveCartItem(memberId, request));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("장바구니 수량 변경으로 음수나 0이 들어올 경우 예외를 던진다.")
    void modifyCartItemQuantity(Integer quantity) {
        // given
        Long memberId = 1L;
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);
        final Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);

        CartItemSaveRequest request = new CartItemSaveRequest(saveLiquorId, 1);
        final Long cartItemId = cartService.saveCartItem(memberId, request);
        final CartItemModifyRequest cartItemModifyRequest = new CartItemModifyRequest(quantity);
        // when & then
        assertThatCode(
            () -> cartService.modifyCartItemQuantity(
                memberId,
                cartItemId,
                cartItemModifyRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(INVALID_QUANTITY_SIZE.getMessage());
    }
}
