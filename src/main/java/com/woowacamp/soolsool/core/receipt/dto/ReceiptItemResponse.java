package com.woowacamp.soolsool.core.receipt.dto;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReceiptItemResponse {

    private final Long liquorId;
    private final int quantity;

    public static ReceiptItemResponse from(final ReceiptItem receiptItem) {
        return new ReceiptItemResponse(
            receiptItem.getLiquor().getId(),
            receiptItem.getQuantity()
        );
    }
}
