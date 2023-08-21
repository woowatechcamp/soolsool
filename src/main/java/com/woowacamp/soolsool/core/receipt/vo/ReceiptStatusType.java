package com.woowacamp.soolsool.core.receipt.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptStatusType {
    CANCELED("취소됨"),
    COMPLETED("완료됨"),
    ;

    private final String name;
}
