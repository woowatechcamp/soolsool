package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
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
            throw new ShoppingException("회원 전화번호는 13자보다 길 수 없습니다.");

        }
    }

    private void validateIsNotNullOrEmpty(final String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            throw new ShoppingException("회원 전화번호는 null이거나 공백일 수 없습니다.");
        }
    }
}
