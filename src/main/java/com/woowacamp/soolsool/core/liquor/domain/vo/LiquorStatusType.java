package com.woowacamp.soolsool.core.liquor.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorStatusType {

    ON_SALE("판매중", "ON_SALE"),
    STOPPED("판매중지", "STOPPED"),
    ;

    private final String status;
    private final String eStatus;
}
