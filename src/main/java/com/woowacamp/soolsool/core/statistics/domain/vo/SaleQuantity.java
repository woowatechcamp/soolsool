package com.woowacamp.soolsool.core.statistics.domain.vo;

import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
}
