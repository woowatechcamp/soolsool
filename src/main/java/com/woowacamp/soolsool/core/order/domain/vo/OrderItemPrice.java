package com.woowacamp.soolsool.core.order.domain.vo;

import static com.woowacamp.soolsool.core.order.code.OrderErrorCode.INVALID_PRICE_SIZE;
import static com.woowacamp.soolsool.core.order.code.OrderErrorCode.NO_PRICE_CONTENT;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OrderItemPrice {

    private final BigInteger price;

    public OrderItemPrice(final BigInteger price) {
        validateIsNotNull(price);
        validateIsValidSize(price);

        this.price = price;
    }

    public static OrderItemPrice from(final String price) {
        return new OrderItemPrice(new BigInteger(price));
    }

    private void validateIsValidSize(final BigInteger price) {
        if (price.compareTo(BigInteger.ZERO) <= 0) {
            throw new SoolSoolException(INVALID_PRICE_SIZE);
        }
    }

    private void validateIsNotNull(final BigInteger price) {
        if (Objects.isNull(price)) {
            throw new SoolSoolException(NO_PRICE_CONTENT);
        }
    }
}
