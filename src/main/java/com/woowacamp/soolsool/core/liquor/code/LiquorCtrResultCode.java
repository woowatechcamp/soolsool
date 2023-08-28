package com.woowacamp.soolsool.core.liquor.code;

import static org.springframework.http.HttpStatus.OK;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorCtrResultCode implements ResultCode {
    
    FIND_LQUOR_CTR_SUCCESS(OK.value(), "LC001", "술 클릭률 조회에 성공했습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
