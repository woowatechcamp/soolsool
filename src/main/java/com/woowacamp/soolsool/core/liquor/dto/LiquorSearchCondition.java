package com.woowacamp.soolsool.core.liquor.dto;

import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LiquorSearchCondition {

    private final LiquorRegion liquorRegion;
    private final LiquorBrew liquorBrew;
    private final LiquorStatus liquorStatus;
    private final String brand;

    public static LiquorSearchCondition nullObject() {
        return new LiquorSearchCondition(
            null,
            null,
            null,
            null
        );
    }
}
