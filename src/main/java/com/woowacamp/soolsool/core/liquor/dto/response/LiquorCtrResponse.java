package com.woowacamp.soolsool.core.liquor.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class LiquorCtrResponse {

    private final double ctr;

    @JsonCreator
    public LiquorCtrResponse(final double ctr) {
        this.ctr = ctr;
    }
}
