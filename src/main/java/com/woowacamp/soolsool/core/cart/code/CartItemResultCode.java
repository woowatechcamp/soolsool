package com.woowacamp.soolsool.core.cart.code;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartItemResultCode implements ResultCode {

    CART_ITEM_ADD_SUCCESS(201, "C201", "장바구니 상품 등록이 완료되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
