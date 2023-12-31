package com.woowacamp.soolsool.core.payment.dto.response;

import com.woowacamp.soolsool.core.order.domain.OrderPaymentInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PayApproveResponse {

    private static final String MONEY = "MONEY";
    
    private final String paymentMethodType;
    private final String purchaseCorp;
    private final String bin;
    private final String installMonth;
    private final String approvedId;
    private final String cardMid;

    public PayApproveResponse(final String paymentMethodType) {
        this(paymentMethodType, null, null, null, null, null);
    }

    public OrderPaymentInfo toEntity(final Long orderId) {
        return new OrderPaymentInfo(
            orderId,
            paymentMethodType,
            purchaseCorp,
            bin,
            installMonth,
            approvedId,
            cardMid
        );
    }
}
