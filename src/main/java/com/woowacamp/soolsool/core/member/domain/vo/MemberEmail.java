package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class MemberEmail {

    private static final Pattern EMAIL_PATTERN = Pattern
        .compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final int MAX_LENGTH = 255;

    private final String email;

    public MemberEmail(final String email) {
        validateIsNotNullOrEmpty(email);
        validateIsValidLength(email);
        validateIsValidFormat(email);

        this.email = email;
    }

    private void validateIsValidLength(final String email) {
        if (email.length() > MAX_LENGTH) {
            throw new SoolSoolException(MemberErrorCode.INVALID_LENGTH_EMAIL);
        }
    }

    private void validateIsNotNullOrEmpty(final String email) {
        if (!StringUtils.hasText(email)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_EMAIL);
        }
    }

    private void validateIsValidFormat(final String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new SoolSoolException(MemberErrorCode.INVALID_FORMAT_EMAIL);
        }
    }
}
