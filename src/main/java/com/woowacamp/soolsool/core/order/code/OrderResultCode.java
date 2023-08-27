package com.woowacamp.soolsool.core.order.code;

import static org.springframework.http.HttpStatus.OK;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderResultCode implements ResultCode {

    ORDER_DETAIL_SUCCESS(OK.value(), "O101", "주문 상세내역 조회가 완료되었습니다."),
    ORDER_LIST_SUCCESS(OK.value(), "O102", "주문내역 목록 조회가 완료되었습니다."),
    ORDER_RATIO_SUCCESS(OK.value(), "O103", "주문율 조회가 완료되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
