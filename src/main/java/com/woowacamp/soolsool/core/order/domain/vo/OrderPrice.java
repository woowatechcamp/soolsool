package com.woowacamp.soolsool.core.order.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OrderPrice {

    private final BigInteger price;

    public OrderPrice(final BigInteger price) {
        validateIsNotNull(price);
        validateIsValidSize(price);

        this.price = price;
    }

    public static OrderPrice from(final String price) {
        return new OrderPrice(new BigInteger(price));
    }

    private void validateIsValidSize(final BigInteger price) {
        if (price.compareTo(BigInteger.ZERO) <= 0) {
            throw new ShoppingException("주문 가격은 0 이하일 수 없습니다.");
        }
    }

    private void validateIsNotNull(final BigInteger price) {
        if (Objects.isNull(price)) {
            throw new ShoppingException("주문 가격은 null일 수 없습니다.");
        }
    }
}
