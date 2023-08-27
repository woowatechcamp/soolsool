package com.woowacamp.soolsool.core.payment.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacamp.soolsool.core.payment.dto.response.PayApproveResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoPayApproveResponse {

    @JsonProperty("payment_method_type")
    private final String paymentMethodType;
    @JsonProperty("card_info")
    private final KakaoPayCardInfo cardInfo;

    public PayApproveResponse toPayApproveResponse() {
        return PayApproveResponse.of(paymentMethodType, cardInfo);
    }
}
