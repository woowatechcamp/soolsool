package com.woowacamp.soolsool.core.payment.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class PaySuccessResponse {

    private final Long orderId;

    @JsonCreator
    public PaySuccessResponse(final Long orderId) {
        this.orderId = orderId;
    }
}
