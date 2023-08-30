package com.woowacamp.soolsool.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {

    LIQUOR_STATUS("liquorStatus", 60 * 60 * 24, 100),
    LIQUOR_BREW("liquorBrew", 60 * 60 * 24, 100),
    LIQUOR_REGION("liquorRegion", 60 * 60 * 24, 100),
    MEMBER_ROLE("memberRole", 60 * 60 * 24, 100),
    ORDER_STATUS("orderStatus", 60 * 60 * 24, 100),
    RECEIPT_STATUS("receiptStatus", 60 * 60 * 24, 100),
    LIQUOR_FIRST("liquorsFirstPage", 5, 200),
    ;

    private final String cacheName;
    private final int expiredSecondAfterWrite;
    private final int maximumSize;
}
