package com.woowacamp.soolsool.core.cart.domain;

import com.woowacamp.soolsool.core.cart.code.CartErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            .anyMatch(cartItem -> cartItem.hasDifferentMemberIdWith(memberId));

        if (hasNotSameMember) {
            throw new SoolSoolException(CartErrorCode.NOT_EQUALS_MEMBER);
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
        if (Objects.isNull(newCartItem)) {
            throw new SoolSoolException(CartErrorCode.NULL_LIQUOR);
        }
    }

    private void validateExceedMaxSize() {
        if (cartItems.size() == MAX_CART_SIZE) {
            throw new SoolSoolException(CartErrorCode.EXCEED_MAX_CART_SIZE);
        }
    }

    private void validateDuplicated(final CartItem newCartItem) {
        if (cartItems.stream().anyMatch(newCartItem::hasSameLiquorWith)) {
            throw new SoolSoolException(CartErrorCode.EXISTS_CART_ITEM);
        }
    }

    private void validateLiquorStatus(final CartItem newCartItem) {
        if (newCartItem.hasStoppedLiquor()) {
            throw new SoolSoolException(CartErrorCode.STOPPED_LIQUOR);
        }
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<CartItem> getCartItems() {
        return cartItems.stream()
            .collect(Collectors.toUnmodifiableList());
    }
}
