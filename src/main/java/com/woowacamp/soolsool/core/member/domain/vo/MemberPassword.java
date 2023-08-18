package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.core.member.exception.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class MemberPassword {

    private static final int MAX_LENGTH = 60;

    private final String password;

    public MemberPassword(final String password) {
        validateIsNotNullOrEmpty(password);
        validateIsValidLength(password);

        this.password = password;
    }

    private void validateIsValidLength(final String password) {
        if (password.length() > MAX_LENGTH) {
            throw new SoolSoolException(MemberErrorCode.INVALID_LENGTH_PASSWORD);

        }
    }

    private void validateIsNotNullOrEmpty(final String password) {
        if (!StringUtils.hasText(password)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_PASSWORD);
        }
    }

    public boolean matchPassword(final String password) {
        return this.password.equals(password);
    }
}
