package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.core.member.exception.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class MemberName {

    private static final int MAX_LENGTH = 13;

    private final String name;

    public MemberName(final String name) {
        validateIsNotNullOrEmpty(name);
        validateIsValidLength(name);

        this.name = name;
    }

    private void validateIsValidLength(final String name) {
        if (name.length() > MAX_LENGTH) {
            throw new SoolSoolException(MemberErrorCode.INVALID_LENGTH_NAME);
        }
    }

    private void validateIsNotNullOrEmpty(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_NAME);
        }
    }
}
