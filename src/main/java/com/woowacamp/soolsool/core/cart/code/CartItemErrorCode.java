package com.woowacamp.soolsool.core.cart.code;

import com.woowacamp.soolsool.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartItemErrorCode implements ErrorCode {

    NOT_FOUND_LIQUOR(400, "C101", "해당 상품이 존재하지 않아 장바구니에 추가할 수 없습니다."),
    NOT_EQUALS_MEMBER(400, "C102", "다른 사용자의 장바구니 상품을 가지고 있습니다."),
    EXCEED_MAX_CART_SIZE(400, "C103", "장바구니가 가득 찼습니다."),
    EXISTS_CART_ITEM(400, "C104", "장바구니에 이미 존재하는 상품입니다."),
    NULL_LIQUOR(500, "C105", "상품이 존재하지 않습니다. 관리자에게 문의해 주세요."),
    STOPPED_LIQUOR(400, "C106", "판매가 중지된 상품은 추가할 수 없습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
