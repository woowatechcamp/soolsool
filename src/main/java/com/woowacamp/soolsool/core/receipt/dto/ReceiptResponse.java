package com.woowacamp.soolsool.core.receipt.dto;

import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReceiptResponse {

    private final Long id;
    private final Long memberId;
    private final ReceiptStatus receiptStatus;
    private final String originalTotalPrice;
    private final String mileageUsage;
    private final String purchasedTotalPrice;
    private final int totalQuantity;
    private final List<ReceiptItemResponse> receiptItemResponse;

    public static ReceiptResponse from(final Receipt receipt) {
        final List<ReceiptItemResponse> items = receipt
            .getReceiptItems()
            .stream()
            .map(ReceiptItemResponse::from)
            .collect(Collectors.toList());
        
        return new ReceiptResponse(
            receipt.getId(),
            receipt.getMemberId(),
            receipt.getReceiptStatus(),
            receipt.getOriginalTotalPrice().toString(),
            receipt.getMileageUsage().toString(),
            receipt.getPurchasedTotalPrice().toString(),
            receipt.getTotalQuantity(),
            items
        );
    }
}
