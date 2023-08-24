package com.woowacamp.soolsool.core.payment.dto.response;

import com.woowacamp.soolsool.core.payment.domain.Payment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PayApproveResponse {

    private final String paymentMethodType;
    private final String purchaseCorp;
    private final String bin;
    private final String installMonth;
    private final String approvedId;
    private final String cardMid;

    public Payment toPayment() {
        return new Payment(
            paymentMethodType,
            purchaseCorp,
            bin,
            installMonth,
            approvedId,
            cardMid
        );
    }
}
