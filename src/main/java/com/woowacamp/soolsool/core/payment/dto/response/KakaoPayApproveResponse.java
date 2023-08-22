package com.woowacamp.soolsool.core.payment.dto.response;

import lombok.Data;

@Data
public class KakaoPayApproveResponse {

    private String payment_method_type;
    private KakaoPayCardInfo card_info;
}
