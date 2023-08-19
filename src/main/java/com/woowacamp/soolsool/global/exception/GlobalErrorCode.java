package com.woowacamp.soolsool.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    NO_CONTENT(BAD_REQUEST.value(), "G101", "null일 수 없습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
