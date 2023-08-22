package com.woowacamp.soolsool.core.payment.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoPayReadyRequest {

    private final String cid;
    private final String partnerUserId;
    private final String partnerOrderId;
    private final String itemName;
    private final Integer quantity;
    private final Integer totalAmount;
    private final Integer taxFreeAmount;
    private final String approvalUrl;
    private final String cancelUrl;
    private final String failUrl;
}
