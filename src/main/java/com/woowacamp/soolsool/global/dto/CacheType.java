package com.woowacamp.soolsool.global.dto;

import lombok.Getter;

@Getter
public enum CacheType {
    LIQUOR_STATUS("liquorStatus", 10, 100),
    LIQUOR_BREW("liquorBrew", 10, 100),
    LIQUOR_REGION("liquorRegion", 10, 100),
    MEMBER_ROLE("memberRole", 10, 100),
    ORDER_STATUS("orderStatus", 10, 100),
    RECEIPT_STATUS("receiptStatus", 10, 100);

    CacheType(final String cacheName, final int expireAfterWrite, final int maximumSize) {
        this.cacheName = cacheName;
        this.expireAfterWrite = expireAfterWrite;
        this.maximumSize = maximumSize;
    }

    private final String cacheName;
    private final int expireAfterWrite;
    private final int maximumSize;
}
