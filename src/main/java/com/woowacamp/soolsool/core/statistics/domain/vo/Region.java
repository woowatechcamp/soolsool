package com.woowacamp.soolsool.core.statistics.domain.vo;

import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class Region {

    private static final int MAX_LENGTH = 255;

    private final String name;

    public Region(final String name) {
        validateIsNotNullOrEmpty(name);
        validateIsValidLength(name);

        this.name = name;
    }


    private void validateIsValidLength(final String region) {
        if (region.length() > MAX_LENGTH) {
            throw new SoolSoolException(MemberErrorCode.INVALID_LENGTH_ADDRESS);
        }
    }

    private void validateIsNotNullOrEmpty(final String region) {
        if (!StringUtils.hasText(region)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_ADDRESS);
        }
    }
}
