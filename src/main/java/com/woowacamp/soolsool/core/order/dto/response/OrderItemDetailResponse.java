package com.woowacamp.soolsool.core.order.dto.response;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderItemDetailResponse {

    private final String liquorBrewName;
    private final String liquorRegionName;
    private final String liquorName;
    private final String liquorOriginalPrice;
    private final String liquorPurchasedPrice;
    private final String liquorBrand;
    private final String liquorImageUrl;
    private final Double liquorAlcohol;
    private final Integer volume;
    private final Integer quantity;

    public static OrderItemDetailResponse from(final ReceiptItem receiptItem) {
        return new OrderItemDetailResponse(
            receiptItem.getReceiptItemBrew(),
            receiptItem.getReceiptItemRegion(),
            receiptItem.getReceiptItemName(),
            receiptItem.getReceiptItemOriginalPrice().toString(),
            receiptItem.getReceiptItemPurchasedPrice().toString(),
            receiptItem.getReceiptItemBrand(),
            receiptItem.getReceiptItemImageUrl(),
            receiptItem.getReceiptItemAlcohol(),
            receiptItem.getReceiptItemVolume(),
            receiptItem.getQuantity()
        );
    }
}
