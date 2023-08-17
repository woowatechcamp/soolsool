package com.woowacamp.soolsool.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DefaultResultCode implements ResultCode {

    DEFAULT_RESULT_CODE(200, "D101", "정상적으로 응답되었습니다."),
    ;
    private final int status;
    private final String code;
    private final String message;
}
