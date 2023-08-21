package com.woowacamp.soolsool.core.receipt.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.woowacamp.soolsool.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptErrorCode implements ErrorCode {
    INVALID_PRICE_SIZE(400, "R101", "주문서 가격은 0 미만일 수 없습니다."),
    NO_CONTENT_PRICE(400, "R102", "주문서 가격은 null일 수 없습니다."),
    INVALID_QUANTITY_SIZE(400, "R103", "주문서 개수는 0 미만일 수 없습니다."),

    NOT_RECEIPT_FOUND(NOT_FOUND.value(), "R104", "주문서가 존재하지 않습니다."),
    NOT_EQUALS_MEMBER(BAD_REQUEST.value(), "C108", "다른 사용자의 주문서를 가지고 있습니다."),

    ;

    private final int status;
    private final String code;
    private final String message;
}
