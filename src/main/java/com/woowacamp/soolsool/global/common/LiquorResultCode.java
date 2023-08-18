package com.woowacamp.soolsool.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LiquorResultCode implements ResultCode {

    LIQUOR_CREATED(HttpStatus.CREATED.value(), "L101", "술 상품이 정상적으로 생성되었습니다."),
    LIQUOR_DETAIL_FOUND(HttpStatus.OK.value(), "L102", "술 상세 정보가 정상적으로 검색되었습니다."),
    LIQUOR_LIST_FOUND(HttpStatus.OK.value(), "L103", "술 목록이 정상적으로 검색되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
