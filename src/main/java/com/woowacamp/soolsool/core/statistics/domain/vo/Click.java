package com.woowacamp.soolsool.core.statistics.domain.vo;

import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Click {

    private final BigInteger count;

    public Click(final BigInteger count) {
        validateIsValidSize(count);
        validateIsNotNull(count);

        this.count = count;
    }

    private void validateIsValidSize(final BigInteger click) {
        if (click.compareTo(BigInteger.ZERO) < 0) {
            throw new SoolSoolException(MemberErrorCode.INVALID_SIZE_MILEAGE);
        }
    }

    private void validateIsNotNull(final BigInteger click) {
        if (Objects.isNull(click)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_MILEAGE);
        }
    }
}
