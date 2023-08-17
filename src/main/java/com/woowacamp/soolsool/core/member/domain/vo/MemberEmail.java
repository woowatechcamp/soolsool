package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class MemberEmail {

    private static final Pattern emailPattern = Pattern
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
            throw new ShoppingException("회원 이메일은 255자보다 길 수 없습니다.");

        }
    }

    private void validateIsNotNullOrEmpty(final String email) {
        if (!StringUtils.hasText(email)) {
            throw new ShoppingException("회원 이메일은 null이거나 공백일 수 없습니다.");
        }
    }

    private void validateIsValidFormat(final String email) {
        if (!emailPattern.matcher(email).matches()) {
            throw new ShoppingException("회원 이메일이 표준 이메일 양식에 맞지 않습니다.");
        }
    }
}
