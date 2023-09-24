package com.woowacamp.soolsool.core.statistics.domain.vo;

import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigInteger;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public class SaleQuantity {

    private final BigInteger quantity;

    public SaleQuantity(final BigInteger quantity) {
        validateIsValidSize(quantity);
        validateIsNotNull(quantity);

        this.quantity = quantity;
    }

    private void validateIsValidSize(final BigInteger saleQuantity) {
        if (saleQuantity.compareTo(BigInteger.ZERO) < 0) {
            throw new SoolSoolException(MemberErrorCode.INVALID_SIZE_MILEAGE);
        }
    }

    private void validateIsNotNull(final BigInteger saleQuantity) {
        if (Objects.isNull(saleQuantity)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_MILEAGE);
        }
    }

    public SaleQuantity add(final BigInteger value) {
        return new SaleQuantity(this.quantity.add(value));
    }
}
