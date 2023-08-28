package com.woowacamp.soolsool.core.payment.infra.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import lombok.Getter;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class KakaoPayReadyResponse {

    private String tid;
    private String nextRedirectPcUrl;

    public PayReadyResponse toReadyResponse() {
        return new PayReadyResponse(tid, nextRedirectPcUrl, null, null);
    }
}
