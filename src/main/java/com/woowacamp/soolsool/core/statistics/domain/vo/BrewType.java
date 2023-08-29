package com.woowacamp.soolsool.core.statistics.domain.vo;

import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class BrewType {

    private static final int MAX_LENGTH = 20;

    private final String type;

    public BrewType(final String type) {
        validateIsNotNullOrEmpty(type);
        validateIsValidLength(type);

        this.type = type;
    }

    private void validateIsValidLength(final String brewType) {
        if (brewType.length() > MAX_LENGTH) {
            throw new SoolSoolException(MemberErrorCode.INVALID_LENGTH_ADDRESS);
        }
    }

    private void validateIsNotNullOrEmpty(final String brewType) {
        if (!StringUtils.hasText(brewType)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_ADDRESS);
        }
    }
}
