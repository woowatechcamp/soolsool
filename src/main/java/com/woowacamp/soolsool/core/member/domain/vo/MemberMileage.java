package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MemberMileage {

    private final BigInteger mileage;

    public MemberMileage(final BigInteger mileage) {
        validateIsNotNull(mileage);
        validateIsValidSize(mileage);

        this.mileage = mileage;
    }

    private void validateIsValidSize(final BigInteger mileage) {
        if (mileage.compareTo(BigInteger.ZERO) < 0) {
            throw new ShoppingException("회원 마일리지는 0 미만일 수 없습니다.");
        }
    }

    private void validateIsNotNull(final BigInteger mileage) {
        if (Objects.isNull(mileage)) {
            throw new ShoppingException("회원 마일리지는 null일 수 없습니다.");
        }
    }
}
