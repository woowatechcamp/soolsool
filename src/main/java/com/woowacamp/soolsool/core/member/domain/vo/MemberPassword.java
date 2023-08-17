package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
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
            throw new ShoppingException("회원 비밀번호은 60자보다 길 수 없습니다.");

        }
    }

    private void validateIsNotNullOrEmpty(final String password) {
        if (!StringUtils.hasText(password)) {
            throw new ShoppingException("회원 비밀번호는 null이거나 공백일 수 없습니다.");
        }
    }
}
