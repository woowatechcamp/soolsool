package com.woowacamp.soolsool.core.cart.domain.vo;

import com.woowacamp.soolsool.core.cart.domain.code.CartErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CartItemQuantity {

    private static final int MIN_SIZE = 1;
    
    private final int quantity;

    public CartItemQuantity(final int quantity) {
        validateIsValidSize(quantity);

        this.quantity = quantity;
    }

    private void validateIsValidSize(final int quantity) {
        if (quantity < MIN_SIZE) {
            throw new SoolSoolException(CartErrorCode.INVALID_QUANTITY_SIZE);
        }
    }
}
