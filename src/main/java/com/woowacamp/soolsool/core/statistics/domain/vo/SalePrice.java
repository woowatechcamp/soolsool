package com.woowacamp.soolsool.core.statistics.domain.vo;

import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigInteger;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public class SalePrice {

    private final BigInteger price;

    public SalePrice(final BigInteger price) {
        validateIsValidSize(price);
        validateIsNotNull(price);

        this.price = price;
    }

    private void validateIsValidSize(final BigInteger salePrice) {
        if (salePrice.compareTo(BigInteger.ZERO) < 0) {
            throw new SoolSoolException(MemberErrorCode.INVALID_SIZE_MILEAGE);
        }
    }

    private void validateIsNotNull(final BigInteger salePrice) {
        if (Objects.isNull(salePrice)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_MILEAGE);
        }
    }

    public SalePrice add(final BigInteger value) {
        return new SalePrice(this.price.add(value));
    }
}
