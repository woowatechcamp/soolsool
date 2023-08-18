package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.core.member.exception.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class MemberAddress {

    private static final int MAX_LENGTH = 100;

    private final String address;

    public MemberAddress(final String address) {
        validateIsNotNullOrEmpty(address);
        validateIsValidLength(address);

        this.address = address;
    }

    private void validateIsValidLength(final String address) {
        if (address.length() > MAX_LENGTH) {
            throw new SoolSoolException(MemberErrorCode.INVALID_LENGTH_ADDRESS);
        }
    }

    private void validateIsNotNullOrEmpty(final String address) {
        if (!StringUtils.hasText(address)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_ADDRESS);
        }
    }
}
