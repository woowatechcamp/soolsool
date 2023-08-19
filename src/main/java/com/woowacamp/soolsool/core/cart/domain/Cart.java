package com.woowacamp.soolsool.core.cart.domain;

import com.woowacamp.soolsool.core.cart.code.CartItemErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Cart {

    private static final int MAX_CART_SIZE = 100;

    private final Long memberId;
    private final List<CartItem> cartItems;

    public Cart(final Long memberId, final List<CartItem> cartItems) {
        validateMember(memberId, cartItems);

        this.memberId = memberId;
        this.cartItems = cartItems;
    }

    private void validateMember(final Long memberId, final List<CartItem> cartItems) {
        boolean hasNotSameMember = cartItems.stream()
            .anyMatch(cartItem -> !Objects.equals(memberId, cartItem.getMemberId()));

        if (hasNotSameMember) {
            throw new SoolSoolException(CartItemErrorCode.NOT_EQUALS_MEMBER);
        }
    }

    public void addCartItem(final CartItem newCartItem) {
        validateNull(newCartItem);
        validateExceedMaxSize();
        validateDuplicated(newCartItem);
        validateLiquorStatus(newCartItem);

        cartItems.add(newCartItem);
    }

    private void validateNull(final CartItem newCartItem) {
        if (newCartItem == null) {
            throw new SoolSoolException(CartItemErrorCode.NULL_LIQUOR);
        }
    }

    private void validateExceedMaxSize() {
        if (cartItems.size() == MAX_CART_SIZE) {
            throw new SoolSoolException(CartItemErrorCode.EXCEED_MAX_CART_SIZE);
        }
    }

    private void validateDuplicated(final CartItem newCartItem) {
        final boolean hasDuplicatedCartItem = cartItems.stream()
            .anyMatch(newCartItem::isSameWith);

        if (hasDuplicatedCartItem) {
            throw new SoolSoolException(CartItemErrorCode.EXISTS_CART_ITEM);
        }
    }

    private void validateLiquorStatus(final CartItem newCartItem) {
        if (newCartItem.getLiquor().isStopped()) {
            throw new SoolSoolException(CartItemErrorCode.STOPPED_LIQUOR);
        }
    }
}
