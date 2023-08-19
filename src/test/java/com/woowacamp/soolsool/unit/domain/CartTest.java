package com.woowacamp.soolsool.unit.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.woowacamp.soolsool.core.cart.domain.Cart;
import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.cart.domain.vo.CartItemQuantity;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("장바구니 : 도메인 테스트")
class CartTest {

    @Test
    @DisplayName("장바구니를 생성한다.")
    void createCart() {
        // given
        Liquor soju = Liquor.builder().build();
        Liquor beer = Liquor.builder().build();

        List<CartItem> cartItems = List.of(
            new CartItem(1L, 1L, soju, new CartItemQuantity(1)),
            new CartItem(2L, 1L, beer, new CartItemQuantity(1))
        );

        // when & then
        assertDoesNotThrow(() -> new Cart(1L, cartItems));
    }

    @Test
    @DisplayName("장바구니의 memberId와 CartItem의 memberId가 다르면 예외를 던진다.")
    void sameMember() {
        // given
        Liquor soju = Liquor.builder().build();
        Liquor beer = Liquor.builder().build();

        List<CartItem> cartItems = List.of(
            new CartItem(1L, 1L, soju, new CartItemQuantity(1)),
            new CartItem(2L, 2L, beer, new CartItemQuantity(1))
        );

        // when & then
        assertThrows(SoolSoolException.class, () -> new Cart(1L, cartItems));
    }

    @Test
    @DisplayName("새로운 장바구니 상품을 추가할 때 100개를 초과하면 예외를 던진다.")
    void exceedMaxSize() {
        // given
        List<CartItem> cartItems = new ArrayList<>();
        for (long id = 1; id <= 100; id++) {
            cartItems.add(new CartItem(id, 1L, Liquor.builder().build(),
                new CartItemQuantity(1)));
        }

        Cart cart = new Cart(1L, cartItems);

        CartItem newCartItem = new CartItem(101L, 1L, Liquor.builder().build(),
            new CartItemQuantity(1));

        // when & then
        assertThrows(SoolSoolException.class, () -> cart.addCartItem(newCartItem));
    }

    @Test
    @DisplayName("새로운 장바구니 상품을 추가할 때 기존에 존재하는 상품이라면 예외를 던진다.")
    void duplicate() {
        // given
        CartItem cartItem = new CartItem(1L, 1L, Liquor.builder().build(),
            new CartItemQuantity(1));
        CartItem sameCart = new CartItem(1L, 1L, Liquor.builder().build(),
            new CartItemQuantity(1));

        List<CartItem> cartItems = List.of(cartItem);

        Cart cart = new Cart(1L, cartItems);

        // when & then
        assertThrows(SoolSoolException.class, () -> cart.addCartItem(sameCart));
    }

    @Test
    @DisplayName("새로운 장바구니 상품을 추가할 때 판매중지된 상품이라면 예외를 던진다.")
    void stoppedLiquor() {
        // given
        Cart cart = new Cart(1L, List.of());

        Liquor stoppedLiquor = Liquor.builder()
            .status(new LiquorStatus(LiquorStatusType.STOPPED))
            .build();

        CartItem cartItem = new CartItem(1L, 1L, stoppedLiquor,
            new CartItemQuantity(1));

        // when & then
        assertThrows(SoolSoolException.class, () -> cart.addCartItem(cartItem));
    }
}
