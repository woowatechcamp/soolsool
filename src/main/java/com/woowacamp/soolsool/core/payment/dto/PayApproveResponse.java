package com.woowacamp.soolsool.core.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayApproveResponse {

    private String paymentMethodType;
    private String purchase_corp;
    private String kakaopay_purchase_corp;
    private String kakaopay_issuer_corp;
    private String bin;
    private String card_type;
    private String install_month;
    private String approved_id;
    private String card_mid;
    private String interest_free_install;
    private String card_item_code;
}
