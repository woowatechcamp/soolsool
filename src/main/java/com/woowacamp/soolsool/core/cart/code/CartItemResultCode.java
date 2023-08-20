package com.woowacamp.soolsool.core.cart.code;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartItemResultCode implements ResultCode {

    CART_ITEM_ADD_SUCCESS(CREATED.value(), "C101", "장바구니 상품 등록이 완료되었습니다."),
    CART_ITEM_MODIFY_QUANTITY_SUCCESS(OK.value(), "C102", "장바구니 상품 수량 변경이 완료되었습니다."),
    CART_ITEM_LIST_FOUND(OK.value(), "C103", "장바구니 상품 목록이 정상적으로 검색되었습니다."),
    CART_ITEM_DELETED(NO_CONTENT.value(), "C104", "장바구니 상품이 정상적으로 삭제되었습니다."),
    CART_ITEM_LIST_DELETED(NO_CONTENT.value(), "C105", "장바구니의 모든 품목이 정상적으로 삭제되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
