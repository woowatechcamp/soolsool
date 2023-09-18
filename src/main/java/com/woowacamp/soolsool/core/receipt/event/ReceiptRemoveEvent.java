package com.woowacamp.soolsool.core.receipt.event;

import lombok.Getter;

@Getter
public class ReceiptRemoveEvent {

    private final Long receiptId;

    public ReceiptRemoveEvent(final Long receiptId) {
        this.receiptId = receiptId;
    }
}
