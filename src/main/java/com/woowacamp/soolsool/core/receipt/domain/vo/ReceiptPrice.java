package com.woowacamp.soolsool.core.receipt.domain.vo;

import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.INVALID_PRICE_SIZE;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NO_CONTENT_PRICE;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ReceiptPrice {

    private final BigInteger price;

    public ReceiptPrice(final BigInteger price) {
        validateIsNotNull(price);
        validateIsValidSize(price);

        this.price = price;
    }

    private void validateIsValidSize(final BigInteger price) {
        if (price.compareTo(BigInteger.ZERO) < 0) {
            throw new SoolSoolException(INVALID_PRICE_SIZE);
        }
    }

    private void validateIsNotNull(final BigInteger price) {
        if (Objects.isNull(price)) {
            throw new SoolSoolException(NO_CONTENT_PRICE);
        }
    }
}
