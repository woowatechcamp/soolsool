package com.woowacamp.soolsool.core.order.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
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
            throw new ShoppingException("주문 상품 가격은 0 이하일 수 없습니다.");
        }
    }

    private void validateIsNotNull(final BigInteger price) {
        if (Objects.isNull(price)) {
            throw new ShoppingException("주문 상품 가격은 null일 수 없습니다.");
        }
    }
}
