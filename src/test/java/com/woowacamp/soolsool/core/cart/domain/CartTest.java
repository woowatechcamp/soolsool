package com.woowacamp.soolsool.core.cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: Cart")
class CartTest {

    private Liquor soju;
    private Liquor beer;

    @BeforeEach
    void setUpLiquor() {
        soju = mock(Liquor.class);
        beer = mock(Liquor.class);

        when(soju.getId()).thenReturn(1L);
        when(beer.getId()).thenReturn(2L);
    }

    @Test
    @DisplayName("장바구니를 생성한다.")
    void createCart() {
        // given
        List<CartItem> cartItems = List.of(
            new CartItem(1L, soju, 1),
            new CartItem(1L, beer, 1)
        );

        // when & then
        assertDoesNotThrow(() -> new Cart(1L, cartItems));
    }

    @Test
    @DisplayName("장바구니의 memberId와 CartItem의 memberId가 다르면 예외를 던진다.")
    void sameMember() {
        // given
        List<CartItem> cartItems = List.of(
            new CartItem(1L, soju, 1),
            new CartItem(2L, beer, 1)
        );

        // when & then
        assertThatThrownBy(() -> new Cart(1L, cartItems))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("다른 사용자의 장바구니 상품을 가지고 있습니다.");
    }

    @Test
    @DisplayName("새로운 장바구니 상품을 추가할 때 100개를 초과하면 예외를 던진다.")
    void exceedMaxSize() {
        // given
        List<CartItem> cartItems = new ArrayList<>();
        for (long id = 1; id <= 100; id++) {
            // 생성 시 중복 검사를 하지 않으므로 편의상 같은 상품 반복 삽입
            cartItems.add(new CartItem(1L, soju, 1));
        }

        Cart cart = new Cart(1L, cartItems);

        CartItem newCartItem = new CartItem(1L, beer, 1);

        // when & then
        assertThatThrownBy(() -> cart.addCartItem(newCartItem))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("장바구니가 가득 찼습니다.");
    }

    @Test
    @DisplayName("새로운 장바구니 상품을 추가할 때 기존에 존재하는 상품이라면 예외를 던진다.")
    void duplicate() {
        // given
        CartItem cartItem = new CartItem(1L, soju, 1);
        CartItem sameCartItem = new CartItem(1L, soju, 1);
        List<CartItem> cartItems = new ArrayList<>(List.of(cartItem));

        Cart cart = new Cart(1L, cartItems);

        // when & then
        assertThatThrownBy(() -> cart.addCartItem(sameCartItem))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("장바구니에 이미 존재하는 상품입니다.");
    }

    @Test
    @DisplayName("새로운 장바구니 상품을 추가할 때 판매중지된 상품이라면 예외를 던진다.")
    void stoppedLiquor() {
        // given
        Cart cart = new Cart(1L, List.of());

        CartItem stoppedCartItem = mock(CartItem.class);
        when(stoppedCartItem.hasStoppedLiquor()).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> cart.addCartItem(stoppedCartItem))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("판매가 중지된 상품은 추가할 수 없습니다.");
    }
}
