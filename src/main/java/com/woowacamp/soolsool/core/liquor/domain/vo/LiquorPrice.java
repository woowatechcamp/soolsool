package com.woowacamp.soolsool.core.liquor.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LiquorPrice {

    private final BigInteger price;

    public LiquorPrice(final BigInteger price) {
        validateIsNotNull(price);
        validateIsValidSize(price);

        this.price = price;
    }

    public static LiquorPrice from(final String price) {
        return new LiquorPrice(new BigInteger(price));
    }

    private void validateIsValidSize(final BigInteger price) {
        if (price.compareTo(BigInteger.ZERO) < 0) {
            throw new ShoppingException("술 가격은 0 미만일 수 없습니다.");
        }
    }

    private void validateIsNotNull(final BigInteger price) {
        if (Objects.isNull(price)) {
            throw new ShoppingException("술 가격은 null일 수 없습니다.");
        }
    }
}
