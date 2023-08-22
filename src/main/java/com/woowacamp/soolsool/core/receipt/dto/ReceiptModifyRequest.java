package com.woowacamp.soolsool.core.receipt.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class ReceiptModifyRequest {

    private final String receiptStatusType;

    @JsonCreator
    public ReceiptModifyRequest(final String receiptStatusType) {
        this.receiptStatusType = receiptStatusType;
    }
}
