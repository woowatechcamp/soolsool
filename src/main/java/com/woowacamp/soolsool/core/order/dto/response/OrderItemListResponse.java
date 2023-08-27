package com.woowacamp.soolsool.core.order.dto.response;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderItemListResponse {

    private final Long liquorId;
    private final String liquorName;
    private final String liquorImageUrl;

    public static OrderItemListResponse from(final ReceiptItem receiptItem) {
        return new OrderItemListResponse(
            receiptItem.getLiquorId(),
            receiptItem.getReceiptItemName(),
            receiptItem.getReceiptItemImageUrl()
        );
    }
}
