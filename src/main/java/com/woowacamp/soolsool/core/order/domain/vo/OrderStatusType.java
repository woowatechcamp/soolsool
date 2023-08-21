package com.woowacamp.soolsool.core.order.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusType {

    COMPLETED("결제완료"),
    CANCELED("결제취소"),
    ;

    private final String status;
}
