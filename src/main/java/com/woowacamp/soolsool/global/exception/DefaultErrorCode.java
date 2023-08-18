package com.woowacamp.soolsool.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DefaultErrorCode implements ErrorCode {

    DEFAULT_ERROR(400, "D101", "기본 예외입니다."),
    DEFAULT_VALIDATION_ERROR(400, "D102", "요청 정보 오류입니다."),
    MEMBER_NO_INFORMATION(400, "M101", "회원 정보를 찾을 수 없습니다."),
    MEMBER_NO_ROLE_TYPE(400, "M101", "일치하는 회원 타입이 없습니다."),
    MEMBER_NO_MATCH_PASSWORD(BAD_REQUEST.value(), "M102", "비밀번호가 틀렸습니다");

    private final int status;
    private final String code;
    private final String message;
}
