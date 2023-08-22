package com.woowacamp.soolsool.core.order.dto.response;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import java.time.LocalDateTime;
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
    private final LocalDateTime expiredAt;

    public static OrderItemDetailResponse from(final ReceiptItem receiptItem){
        return new OrderItemDetailResponse(
            receiptItem.getLiquorBrew().getType().getName(),
            receiptItem.getLiquorRegion().getType().getName(),
            receiptItem.getLiquorName(),
            receiptItem.getLiquorOriginalPrice().toString(),
            receiptItem.getLiquorPurchasedPrice().toString(),
            receiptItem.getLiquorBrand(),
            receiptItem.getLiquorImageUrl(),
            receiptItem.getLiquorAlcohol(),
            receiptItem.getLiquorVolume(),
            receiptItem.getQuantity(),
            receiptItem.getExpiredAt()
        );
    }
}
