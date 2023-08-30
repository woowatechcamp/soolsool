package com.woowacamp.soolsool.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {

    LIQUOR_STATUS("liquorStatus", 1, 100),
    LIQUOR_BREW("liquorBrew", 1, 100),
    LIQUOR_REGION("liquorRegion", 1, 100),
    MEMBER_ROLE("memberRole", 1, 100),
    ORDER_STATUS("orderStatus", 1, 100),
    RECEIPT_STATUS("receiptStatus", 1, 100),
    LIQUOR_FIRST("liquorsFirstPage", 1, 200),
    ;

    private final String cacheName;
    private final int expiredSecondAfterWrite;
    private final int maximumSize;
}
