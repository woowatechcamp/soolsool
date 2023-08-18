package com.woowacamp.soolsool.core.receipt.vo;

import com.woowacamp.soolsool.core.receipt.exception.ReceiptErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ReceiptQuantity {

    private final int quantity;

    public ReceiptQuantity(final int quantity) {
        validateIsNotLessThanZero(quantity);

        this.quantity = quantity;
    }

    private void validateIsNotLessThanZero(final int quantity) {
        if (quantity < 0) {
            throw new SoolSoolException(ReceiptErrorCode.INVALID_QUANTITY_SIZE);
        }
    }
}
