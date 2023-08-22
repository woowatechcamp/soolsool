package com.woowacamp.soolsool.core.order.domain.vo;

import static com.woowacamp.soolsool.core.order.code.OrderErrorCode.NOT_POSITIVE_QUANTITY;
import static com.woowacamp.soolsool.global.code.GlobalErrorCode.NO_CONTENT;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.Objects;
import lombok.Getter;

@Getter
public class OrderQuantity {

    private static final Integer MIN_SIZE = 1;

    private final Integer quantity;

    public OrderQuantity(final Integer quantity) {
        validateIsNotNullable(quantity);
        validateIsPositive(quantity);

        this.quantity = quantity;
    }

    private void validateIsNotNullable(final Integer quantity) {
        if (Objects.isNull(quantity)) {
            throw new SoolSoolException(NO_CONTENT);
        }
    }

    private void validateIsPositive(final Integer quantity) {
        if (quantity < MIN_SIZE) {
            throw new SoolSoolException(NOT_POSITIVE_QUANTITY);
        }
    }
}
