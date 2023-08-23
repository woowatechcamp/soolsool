package com.woowacamp.soolsool.core.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayReadyResponse {

    private String tid;
    private String nextRedirectPcUrl;
    private String nextRedirectMobileUrl;
    private String nextRedirectAppUrl;
}
