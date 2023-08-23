package com.woowacamp.soolsool.core.payment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class KakaoPayReadyResponse {

    private String tid;
    private String next_redirect_pc_url;

    public PayReadyResponse toReadyResponse() {
        return new PayReadyResponse(tid, next_redirect_pc_url, null, null);
    }
}
