package com.woowacamp.soolsool.core.liquor.dto.liquorCtr;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Getter;

@Getter
public class LiquorImpressionAddRequest {

    private final List<Long> liquorIds;

    @JsonCreator
    public LiquorImpressionAddRequest(final List<Long> liquorIds) {
        this.liquorIds = liquorIds;
    }
}
