package com.woowacamp.soolsool.core.payment.infra.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO : final
@Getter
@RequiredArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class KakaoPayCardInfo {

    private final String purchaseCorp;
    private final String issuerCorp;
    private final String kakaoPayPurchaseCorp;
    private final String kakaoPayPurchaseCorpCode;
    private final String bin;
    private final String cardType;
    private final String installMonth;
    private final String approvedId;
    private final String cardMid;
    private final String interestFreeInstall;
    private final String cardItemCode;
}
