package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
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
            throw new ShoppingException("회원 이름은 13자보다 길 수 없습니다.");

        }
    }

    private void validateIsNotNullOrEmpty(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new ShoppingException("회원 이름은 null이거나 공백일 수 없습니다.");
        }
    }
}
