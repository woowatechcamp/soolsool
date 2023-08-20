package com.woowacamp.soolsool.integration.service;

import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.INVALID_QUANTITY_SIZE;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_EQUALS_MEMBER;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_FOUND_CART_ITEM;
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
@DisplayName("CartItemService 통합 테스트")
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
            () -> cartService.addCartItem(memberId, request));
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
        Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);

        CartItemSaveRequest request = new CartItemSaveRequest(saveLiquorId, 1);
        Long cartItemId = cartService.addCartItem(memberId, request);
        CartItemModifyRequest cartItemModifyRequest = new CartItemModifyRequest(quantity);

        // when & then
        assertThatCode(
            () -> cartService.modifyCartItemQuantity(
                memberId,
                cartItemId,
                cartItemModifyRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(INVALID_QUANTITY_SIZE.getMessage());
    }

    @Test
    @DisplayName("다른 사용자가 삭제하려고 할 시, 예외를 던진다.")
    void removeCartItemByNotEqualUser() {
        // given
        Long memberId = 1L;
        Long anotherMemberId = 2L;
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
        Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);
        CartItemSaveRequest request = new CartItemSaveRequest(saveLiquorId, 1);
        Long cartItemId = cartService.addCartItem(memberId, request);

        // when & then
        assertThatCode(() -> cartService.removeCartItem(anotherMemberId, cartItemId))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_EQUALS_MEMBER.getMessage());
    }

    @Test
    @DisplayName("장바구니에 없는 상품을 삭제할 시, 예외를 던진다.")
    void removeNoExistCartItem() {
        // given
        Long memberId = 1L;
        Long wrongCartItemId = 999L;

        // when & then
        assertThatCode(() -> cartService.removeCartItem(memberId, wrongCartItemId))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_FOUND_CART_ITEM.getMessage());
    }
}
