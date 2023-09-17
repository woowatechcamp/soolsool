package com.woowacamp.soolsool.core.liquor.dto.liquorCtr;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class LiquorClickAddRequest {

    private final Long liquorId;

    @JsonCreator
    public LiquorClickAddRequest(final Long liquorId) {
        this.liquorId = liquorId;
    }
}
