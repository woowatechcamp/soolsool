package com.woowacamp.soolsool.core.order.dto.response;

import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.OrderPaymentInfo;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailResponse {

    private final Long orderId;
    private final String orderStatus;
    private final String originalTotalPrice;
    private final String mileageUsage;
    private final String purchasedTotalPrice;
    private final Integer totalQuantity;
    private final LocalDateTime createdAt;
    private final List<OrderItemDetailResponse> orderItems;
    private final OrderPaymentInfoResponse paymentInfo;

    public static OrderDetailResponse of(final Order order, final OrderPaymentInfo orderPaymentInfo) {
        final Receipt receipt = order.getReceipt();

        final List<OrderItemDetailResponse> receiptItems = receipt.getReceiptItems().stream()
            .map(OrderItemDetailResponse::from)
            .collect(Collectors.toUnmodifiableList());

        return new OrderDetailResponse(
            order.getId(),
            order.getStatus().getType().getStatus(),
            receipt.getOriginalTotalPrice().toString(),
            receipt.getMileageUsage().toString(),
            receipt.getPurchasedTotalPrice().toString(),
            receipt.getTotalQuantity(),
            order.getCreatedAt(),
            receiptItems,
            OrderPaymentInfoResponse.from(orderPaymentInfo)
        );
    }
}
