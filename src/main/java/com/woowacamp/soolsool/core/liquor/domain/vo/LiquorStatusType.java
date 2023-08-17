package com.woowacamp.soolsool.core.liquor.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorStatusType {

    ON_SALE("판매중"),
    STOPPED("판매중지"),
    ;

    private final String status;
}


