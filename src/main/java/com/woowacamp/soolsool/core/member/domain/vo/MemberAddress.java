package com.woowacamp.soolsool.core.member.domain.vo;

import com.woowacamp.soolsool.global.exception.ShoppingException;
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
            throw new ShoppingException("회원 주소는 100자보다 길 수 없습니다.");

        }
    }

    private void validateIsNotNullOrEmpty(final String address) {
        if (!StringUtils.hasText(address)) {
            throw new ShoppingException("회원 주소는 null이거나 공백일 수 없습니다.");
        }
    }
}
