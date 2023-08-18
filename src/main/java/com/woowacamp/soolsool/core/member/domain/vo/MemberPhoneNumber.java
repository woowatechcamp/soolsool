package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.core.member.exception.MemberErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
public class MemberPhoneNumber {

    private static final int MAX_LENGTH = 13;

    private final String phoneNumber;

    public MemberPhoneNumber(final String phoneNumber) {
        validateIsNotNullOrEmpty(phoneNumber);
        validateIsValidLength(phoneNumber);

        this.phoneNumber = phoneNumber;
    }

    private void validateIsValidLength(final String phoneNumber) {
        if (phoneNumber.length() > MAX_LENGTH) {
            throw new SoolSoolException(MemberErrorCode.INVALID_LENGTH_PHONE_NUMBER);
        }
    }

    private void validateIsNotNullOrEmpty(final String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_PHONE_NUMBER);
        }
    }
}
