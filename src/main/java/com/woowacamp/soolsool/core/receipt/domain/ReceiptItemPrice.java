package com.woowacamp.soolsool.core.receipt.domain;

import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.INVALID_SIZE_PRICE;
import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NO_CONTENT_PRICE;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ReceiptItemPrice {

    private final BigInteger price;

    public ReceiptItemPrice(final BigInteger price) {
        validateIsNotNull(price);
        validateIsValidSize(price);

        this.price = price;
    }

    public static ReceiptItemPrice from(final String price) {
        return new ReceiptItemPrice(new BigInteger(price));
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
