package com.woowacamp.soolsool.core.receipt.code;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptResultCode implements ResultCode {

    RECEIPT_ADD_SUCCESS(CREATED.value(), "R101", "주문서 등록이 완료되었습니다."),
    RECEIPT_MODIFY_QUANTITY_SUCCESS(OK.value(), "R102", "주문서 수량 변경이 완료되었습니다."),
    RECEIPT_LIST_FOUND(OK.value(), "R103", "주문서 목록이 정상적으로 검색되었습니다."),
    RECEIPT_DELETED(NO_CONTENT.value(), "R104", "주문서가 정상적으로 삭제되었습니다."),
    RECEIPT_LIST_DELETED(NO_CONTENT.value(), "R105", "주문서가 정상적으로 삭제되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
