package com.woowacamp.soolsool.core.cart.domain.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.woowacamp.soolsool.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartErrorCode implements ErrorCode {

    INVALID_QUANTITY_SIZE(BAD_REQUEST.value(), "C101", "장바구니 상품 수량은 1개 이상이어야 합니다.");

    private final int status;
    private final String code;
    private final String message;
}
