package com.woowacamp.soolsool.core.receipt.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.woowacamp.soolsool.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptErrorCode implements ErrorCode {

    NOT_FOUND_LIQUOR(BAD_REQUEST.value(), "C101", "해당 상품이 존재하지 않아 장바구니에 추가할 수 없습니다."),

    INVALID_QUANTITY_SIZE(BAD_REQUEST.value(), "C102", "장바구니 상품 수량은 1개 이상이어야 합니다."),
    EXCEED_MAX_CART_SIZE(BAD_REQUEST.value(), "C103", "장바구니가 가득 찼습니다."),

    STOPPED_LIQUOR(BAD_REQUEST.value(), "C104", "판매가 중지된 상품은 추가할 수 없습니다."),
    NULL_LIQUOR(INTERNAL_SERVER_ERROR.value(), "C105", "상품이 존재하지 않습니다. 관리자에게 문의해 주세요."),

    EXISTS_CART_ITEM(BAD_REQUEST.value(), "C106", "장바구니에 이미 존재하는 상품입니다."),
    NOT_FOUND_CART_ITEM(NOT_FOUND.value(), "C107", "장바구니 상품이 존재하지 않습니다."),

    NOT_EQUALS_MEMBER(BAD_REQUEST.value(), "C108", "다른 사용자의 장바구니 상품을 가지고 있습니다."),

    ;

    private final int status;
    private final String code;
    private final String message;
}
