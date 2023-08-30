package com.woowacamp.soolsool.global.infra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LockType {

    MEMBER("MEMBER:"),
    LIQUOR_CTR("LIQUOR_CTR:"),
    LIQUOR_STOCK("LIQUOR_STOCK:"),
    ORDER("ORDER:"),
    ;

    private final String key;
}
