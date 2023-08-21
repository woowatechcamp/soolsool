package com.woowacamp.soolsool.core.receipt.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptStatusType {
    CANCELED("취소됨"),
    COMPLETED("완료됨"),
    INPROGRESS("진행중"),
    EXPIRED("만료됨"),
    ;

    private final String name;
}
