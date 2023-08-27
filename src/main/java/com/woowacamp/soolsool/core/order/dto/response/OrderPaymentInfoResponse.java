package com.woowacamp.soolsool.core.order.dto.response;

import com.woowacamp.soolsool.core.order.domain.OrderPaymentInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderPaymentInfoResponse {

    private final String paymentMethodType;
    private final String purchaseCorp;
    private final String bin;
    private final String installMonth;
    private final String approvedId;
    private final String cardMid;

    public static OrderPaymentInfoResponse from(final OrderPaymentInfo orderPaymentInfo) {
        return new OrderPaymentInfoResponse(
            orderPaymentInfo.getPaymentMethodType(),
            orderPaymentInfo.getPurchaseCorp(),
            orderPaymentInfo.getBin(),
            orderPaymentInfo.getInstallMonth(),
            orderPaymentInfo.getApprovedId(),
            orderPaymentInfo.getCardMid()
        );
    }
}
