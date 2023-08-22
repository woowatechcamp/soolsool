package com.woowacamp.soolsool.core.liquor.domain.vo;

import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.INVALID_SIZE_PRICE;
import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NO_CONTENT_PRICE;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
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
            throw new SoolSoolException(INVALID_SIZE_PRICE);
        }
    }

    private void validateIsNotNull(final BigInteger price) {
        if (Objects.isNull(price)) {
            throw new SoolSoolException(NO_CONTENT_PRICE);
        }
    }
}
