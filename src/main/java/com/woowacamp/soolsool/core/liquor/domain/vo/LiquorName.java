package com.woowacamp.soolsool.core.liquor.domain.vo;

import com.woowacamp.soolsool.core.liquor.exception.LiquorErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
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
            throw new SoolSoolException(LiquorErrorCode.INVALID_LENGTH_NAME);
        }
    }

    private void validateIsNotNullOrEmpty(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new SoolSoolException(LiquorErrorCode.NO_CONTENT_NAME);
        }
    }
}
