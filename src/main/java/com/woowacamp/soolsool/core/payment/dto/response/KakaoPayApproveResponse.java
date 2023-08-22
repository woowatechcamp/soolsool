package com.woowacamp.soolsool.core.payment.dto.response;

import com.woowacamp.soolsool.core.payment.dto.PayApproveResponse;
import lombok.Data;

@Data
public class KakaoPayApproveResponse {

    private String payment_method_type;
    private KakaoPayCardInfo card_info;

    public PayApproveResponse toApproveResponse() {
        return new PayApproveResponse(
            payment_method_type,
            card_info.getPurchase_corp(),
            card_info.getKakaopay_purchase_corp(),
            card_info.getKakaopay_issuer_corp(),
            card_info.getBin(),
            card_info.getCard_type(),
            card_info.getInstall_month(),
            card_info.getApproved_id(),
            card_info.getCard_mid(),
            card_info.getInterest_free_install(),
            card_info.getCard_item_code()
        );
    }
}
