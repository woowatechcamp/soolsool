package com.woowacamp.soolsool.core.payment.infra.dto.response;

import com.woowacamp.soolsool.core.payment.dto.response.PayApproveResponse;
import lombok.Data;

@Data
public class KakaoPayApproveResponse {

    private String payment_method_type;
    private KakaoPayCardInfo card_info;

    public PayApproveResponse toPayApproveResponse() {
        return new PayApproveResponse(
            payment_method_type,
            card_info.getPurchase_corp(),
            card_info.getBin(),
            card_info.getInstall_month(),
            card_info.getApproved_id(),
            card_info.getCard_mid()
        );
    }
}
