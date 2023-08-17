package com.woowacamp.soolsool.core.liquor.domain.vo;

import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_STATUS_TYPE_FOUND;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
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

    public static LiquorStatusType findType(final String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new SoolSoolException(NOT_LIQUOR_STATUS_TYPE_FOUND);
        }
    }
}
