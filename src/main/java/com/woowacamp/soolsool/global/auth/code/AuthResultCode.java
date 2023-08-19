package com.woowacamp.soolsool.global.auth.code;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthResultCode implements ResultCode {

    LOGIN_SUCCESS(200, "A101", "성공적으로 로그인되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
