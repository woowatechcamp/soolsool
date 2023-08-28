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
    ORDER_STATUS("orderStatus", 10, 100),
    RECEIPT_STATUS("receiptStatus", 10, 100),
    LIQUOR_FIRST("liquorsFirstPage", 10, 200),
    ;

    private final String cacheName;
    private final int expireAfterWrite;
    private final int maximumSize;
}
