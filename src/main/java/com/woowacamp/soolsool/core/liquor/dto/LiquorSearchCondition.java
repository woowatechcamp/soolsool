package com.woowacamp.soolsool.core.liquor.dto;

import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LiquorSearchCondition {

    private final Optional<LiquorRegion> liquorRegion;
    private final Optional<LiquorBrew> liquorBrew;
    private final Optional<LiquorStatus> liquorStatus;
    private final String brand;
}
