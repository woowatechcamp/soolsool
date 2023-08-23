package com.woowacamp.soolsool.core.payment.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoPayApproveRequest {

    private final String cid;
    private final String tid;
    private final String partnerOrderId;
    private final String partnerUserId;
    private final String pgToken;
}
