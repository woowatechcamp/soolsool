package com.woowacamp.soolsool.core.payment.infra.dto.response;

import com.woowacamp.soolsool.core.payment.dto.response.PayApproveResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoPayApproveResponse {

    private final String paymentMethodType;
    private final KakaoPayCardInfo cardInfo;

    public PayApproveResponse toPayApproveResponse() {
        return PayApproveResponse.of(paymentMethodType, cardInfo);
    }
}
