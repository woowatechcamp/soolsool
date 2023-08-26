package com.woowacamp.soolsool.core.order.dto.response;

import com.woowacamp.soolsool.core.order.domain.PaymentInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentInfoResponse {

    private final String paymentMethodType;
    private final String purchaseCorp;
    private final String bin;
    private final String installMonth;
    private final String approvedId;
    private final String cardMid;

    public static PaymentInfoResponse from(final PaymentInfo paymentInfo) {
        return new PaymentInfoResponse(
            paymentInfo.getPaymentMethodType(),
            paymentInfo.getPurchaseCorp(),
            paymentInfo.getBin(),
            paymentInfo.getInstallMonth(),
            paymentInfo.getApprovedId(),
            paymentInfo.getCardMid()
        );
    }
}
