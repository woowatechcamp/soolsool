package com.woowacamp.soolsool.core.liquor.domain.vo;


import com.woowacamp.soolsool.global.exception.ShoppingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class LiquorName {

    private static final int MAX_LENGTH = 30;

    private final String name;

    public LiquorName(final String name) {
        validateIsNotNullOrEmpty(name);
        validateIsValidLength(name);

        this.name = name;
    }

    private void validateIsValidLength(final String name) {
        if (name.length() > MAX_LENGTH) {
            throw new ShoppingException("술 이름은 30자보다 길 수 없습니다.");

        }
    }

    private void validateIsNotNullOrEmpty(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new ShoppingException("술 이름은 null이거나 공백일 수 없습니다.");
        }
    }
}
