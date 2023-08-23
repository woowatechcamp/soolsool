package com.woowacamp.soolsool.core.payment.code;

import static org.springframework.http.HttpStatus.OK;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayResultCode implements ResultCode {

    PAY_READY_SUCCESS(OK.value(), "P101", "결제 요청이 완료되었습니다."),
    PAY_APPROVE_SUCCESS(OK.value(), "P102", "결제 승인이 완료되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
