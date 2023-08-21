package com.woowacamp.soolsool.core.cart.domain;

import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.EXCEED_MAX_CART_SIZE;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.EXISTS_CART_ITEM;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_EQUALS_MEMBER;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NULL_LIQUOR;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.STOPPED_LIQUOR;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
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
        if (cartItems.stream().anyMatch(cartItem -> cartItem.hasDifferentMemberIdWith(memberId))) {
            throw new SoolSoolException(NOT_EQUALS_MEMBER);
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
            throw new SoolSoolException(NULL_LIQUOR);
        }
    }

    private void validateExceedMaxSize() {
        if (cartItems.size() == MAX_CART_SIZE) {
            throw new SoolSoolException(EXCEED_MAX_CART_SIZE);
        }
    }

    private void validateDuplicated(final CartItem newCartItem) {
        if (cartItems.stream().anyMatch(newCartItem::hasSameLiquorWith)) {
            throw new SoolSoolException(EXISTS_CART_ITEM);
        }
    }

    private void validateLiquorStatus(final CartItem newCartItem) {
        if (newCartItem.hasStoppedLiquor()) {
            throw new SoolSoolException(STOPPED_LIQUOR);
        }
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<CartItem> getCartItems() {
        return cartItems.stream()
            .collect(Collectors.toUnmodifiableList());
    }

    public BigInteger getTotalAmount() {
        return cartItems.stream()
            .map(CartItem::getTotalAmount)
            .reduce(BigInteger.ZERO, BigInteger::add);
    }

    public int cartItemSize() {
        return cartItems.size();
    }

}
