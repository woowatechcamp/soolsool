package com.woowacamp.soolsool.core.receipt.domain.vo;

import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.INVALID_QUANTITY_SIZE;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ReceiptItemQuantity {

    private final int quantity;

    public ReceiptItemQuantity(final int quantity) {
        validateIsNotLessThanZero(quantity);

        this.quantity = quantity;
    }

    private void validateIsNotLessThanZero(final int quantity) {
        if (quantity < 0) {
            throw new SoolSoolException(INVALID_QUANTITY_SIZE);
        }
    }
}
