package com.woowacamp.soolsool.core.liquor.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.woowacamp.soolsool.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorCtrErrorCode implements ErrorCode {

    NO_CONTENT_IMPRESSION(INTERNAL_SERVER_ERROR.value(), "LC001", "노출수는 null일 수 없습니다."),
    INVALID_SIZE_IMPRESSION(INTERNAL_SERVER_ERROR.value(), "LC002", "노출수는 음수일 수 없습니다."),

    NO_CONTENT_CLICK(INTERNAL_SERVER_ERROR.value(), "LC003", "클릭수는 null일 수 없습니다."),
    INVALID_SIZE_CLICK(INTERNAL_SERVER_ERROR.value(), "LC004", "클릭수는 음수일 수 없습니다."),
    DIVIDE_BY_ZERO_IMPRESSION(INTERNAL_SERVER_ERROR.value(), "LC005", "노출수가 0일 때 클릭률을 구할 수 없습니다."),
    
    NOT_LIQUOR_CTR_FOUND(BAD_REQUEST.value(), "LC006", "술 클릭률이 존재하지 않습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
