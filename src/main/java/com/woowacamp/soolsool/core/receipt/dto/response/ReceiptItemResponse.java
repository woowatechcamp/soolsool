package com.woowacamp.soolsool.core.receipt.dto.response;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReceiptItemResponse {

    private final Long liquorId;
    private final int quantity;
    private final String brew;
    private final String region;
    private final String name;
    private final String originalPrice;
    private final String purchasedPrice;
    private final String brand;
    private final String imageUrl;
    private final Double alcohol;
    private final Integer volume;

    public static ReceiptItemResponse from(final ReceiptItem receiptItem) {
        return new ReceiptItemResponse(
            receiptItem.getLiquorId(),
            receiptItem.getQuantity(),
            receiptItem.getLiquorBrew(),
            receiptItem.getLiquorRegion(),
            receiptItem.getLiquorName(),
            receiptItem.getLiquorOriginalPrice().toString(),
            receiptItem.getLiquorPurchasedPrice().toString(),
            receiptItem.getLiquorBrand(),
            receiptItem.getLiquorImageUrl(),
            receiptItem.getLiquorAlcohol(),
            receiptItem.getLiquorVolume()
        );
    }
}
