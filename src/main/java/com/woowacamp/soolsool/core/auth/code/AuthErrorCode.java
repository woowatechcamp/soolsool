package com.woowacamp.soolsool.core.auth.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.woowacamp.soolsool.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    TOKEN_ERROR(BAD_REQUEST.value(), "A101", "토큰이 유효하지 않습니다."),
    ;
    
    private final int status;
    private final String code;
    private final String message;
}
