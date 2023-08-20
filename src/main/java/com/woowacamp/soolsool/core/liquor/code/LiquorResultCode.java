package com.woowacamp.soolsool.core.liquor.code;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorResultCode implements ResultCode {

    LIQUOR_CREATED(CREATED.value(), "L101", "술 상품이 정상적으로 생성되었습니다."),
    LIQUOR_DETAIL_FOUND(OK.value(), "L102", "술 상세 정보가 정상적으로 검색되었습니다."),
    LIQUOR_LIST_FOUND(OK.value(), "L103", "술 목록이 정상적으로 검색되었습니다."),
    LIQUOR_UPDATED(OK.value(), "L104", "술 상품이 정상적으로 수정되었습니다."),
    LIQUOR_DELETED(NO_CONTENT.value(), "L105", "술 상품이 정상적으로 삭제되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
