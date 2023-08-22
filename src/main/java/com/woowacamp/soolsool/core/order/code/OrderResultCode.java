package com.woowacamp.soolsool.core.order.code;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderResultCode implements ResultCode {

    ORDER_DETAIL_SUCCESS(HttpStatus.OK.value(), "O101", "주문 상세내역 조회가 완료되었습니다."),
    ORDER_LIST_SUCCESS(HttpStatus.OK.value(), "O101", "주문내역 목록 조회가 완료되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
