package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.core.member.exception.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MemberMileage {

    private final BigInteger mileage;

    public static MemberMileage from(final String mileage) {
        return new MemberMileage(new BigInteger(mileage));
    }

    public MemberMileage(final BigInteger mileage) {
        validateIsNotNull(mileage);
        validateIsValidSize(mileage);

        this.mileage = mileage;
    }

    private void validateIsValidSize(final BigInteger mileage) {
        if (mileage.compareTo(BigInteger.ZERO) < 0) {
            throw new SoolSoolException(MemberErrorCode.INVALID_SIZE_MILEAGE);
        }
    }

    private void validateIsNotNull(final BigInteger mileage) {
        if (Objects.isNull(mileage)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_MILEAGE);
        }
    }
}
