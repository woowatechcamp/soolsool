package com.woowacamp.soolsool.core.payment.infra.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacamp.soolsool.core.payment.dto.response.PayApproveResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO : final
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class KakaoPayApproveResponse {

    private static final String MONEY = "MONEY";

    private String paymentMethodType;
    private KakaoPayCardInfo cardInfo;

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
