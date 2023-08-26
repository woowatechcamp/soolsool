package com.woowacamp.soolsool.core.order.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class OrderRatioResponse {

    private final Double ratio;

    @JsonCreator
    public OrderRatioResponse(final Double ratio) {
        this.ratio = ratio;
    }
}
