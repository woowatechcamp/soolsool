package com.woowacamp.soolsool.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LiquorErrorCode implements ErrorCode {

    NOT_LIQUOR_BREW_TYPE_FOUND(
        HttpStatus.NOT_FOUND.value(), "L101", "brew type이 존재하지 않습니다."),
    NOT_LIQUOR_STATUS_TYPE_FOUND(
        HttpStatus.NOT_FOUND.value(), "L102", "status type이 존재하지 않습니다."),
    NOT_LIQUOR_REGION_TYPE_FOUND(
        HttpStatus.NOT_FOUND.value(), "L103", "region type이 존재하지 않습니다."),
    NOT_LIQUOR_FOUND(
        HttpStatus.NOT_FOUND.value(), "L104", "해당 Liquor가 존재하지 않습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
