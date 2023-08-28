package com.woowacamp.soolsool.core.payment.infra.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacamp.soolsool.core.payment.dto.response.PayApproveResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO : final
@Getter
@RequiredArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class KakaoPayApproveResponse {

    private static final String MONEY = "MONEY";

    private final String paymentMethodType;

    private final KakaoPayCardInfo cardInfo;

    public PayApproveResponse toPayApproveResponse() {
        if (paymentMethodType.equals(MONEY)) {
            return new PayApproveResponse(paymentMethodType);
        }

        return new PayApproveResponse(
            paymentMethodType,
            cardInfo.getPurchaseCorp(),
            cardInfo.getBin(),
            cardInfo.getInstallMonth(),
            cardInfo.getApprovedId(),
            cardInfo.getCardMid()
        );
    }
}
