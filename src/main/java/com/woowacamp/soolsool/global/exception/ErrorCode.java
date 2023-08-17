package com.woowacamp.soolsool.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DEFAULT_ERROR(400, "D101", "기본 예외입니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
