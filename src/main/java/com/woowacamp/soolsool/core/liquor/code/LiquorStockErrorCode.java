package com.woowacamp.soolsool.core.liquor.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.woowacamp.soolsool.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorStockErrorCode implements ErrorCode {

    INCLUDE_OTHER_LIQUOR(BAD_REQUEST.value(), "LS101", "동일한 주류가 아닌 재고가 포함되어 있습니다."),
    EMPTY_LIQUOR_STOCKS(BAD_REQUEST.value(), "LS102", "주류 재고가 존재하지 않습니다."),
    NOT_ENOUGH_LIQUOR_STOCKS(BAD_REQUEST.value(), "LS103", "주류 재고가 부족합니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
