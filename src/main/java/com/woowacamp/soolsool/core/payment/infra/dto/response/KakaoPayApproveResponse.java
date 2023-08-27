package com.woowacamp.soolsool.core.payment.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacamp.soolsool.core.payment.dto.response.PayApproveResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoPayApproveResponse {

    @JsonProperty("payment_method_type")
    private static final String MONEY = "MONEY";

    private final String paymentMethodType;
    @JsonProperty("card_info")
    private final KakaoPayCardInfo cardInfo;

    public PayApproveResponse toPayApproveResponse() {
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
