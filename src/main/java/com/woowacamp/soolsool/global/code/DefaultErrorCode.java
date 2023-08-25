package com.woowacamp.soolsool.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DefaultErrorCode implements ErrorCode {

    DEFAULT_ERROR(400, "D101", "기본 예외입니다."),
    DEFAULT_VALIDATION_ERROR(400, "D102", "요청 정보 오류입니다."),

    UNEXPECTED_ERROR(500, "D103", "예상치 못한 오류가 발생했습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
