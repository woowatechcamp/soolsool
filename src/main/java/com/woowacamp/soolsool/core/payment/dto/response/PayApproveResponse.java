package com.woowacamp.soolsool.core.payment.dto.response;

import com.woowacamp.soolsool.core.payment.domain.Payment;
import com.woowacamp.soolsool.core.payment.infra.dto.response.KakaoPayCardInfo;
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

    public static PayApproveResponse of(
        final String paymentMethodType,
        final KakaoPayCardInfo cardInfo
    ) {
        if (paymentMethodType.equals(MONEY)) {
            return new PayApproveResponse(paymentMethodType);
        }

        return new PayApproveResponse(
            paymentMethodType,
            cardInfo.getPurchase_corp(),
            cardInfo.getBin(),
            cardInfo.getInstall_month(),
            cardInfo.getApproved_id(),
            cardInfo.getCard_mid()
        );
    }
}
