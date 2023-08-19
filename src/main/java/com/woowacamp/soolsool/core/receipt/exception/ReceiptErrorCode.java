package com.woowacamp.soolsool.core.receipt.exception;

import com.woowacamp.soolsool.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptErrorCode implements ErrorCode {

    INVALID_PRICE_SIZE(400, "R101", "주문서 가격은 0 미만일 수 없습니다."),
    NO_CONTENT_PRICE(400, "R102", "주문서 가격은 null일 수 없습니다."),

    INVALID_QUANTITY_SIZE(400, "R103", "주문서 개수는 0 미만일 수 없습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
