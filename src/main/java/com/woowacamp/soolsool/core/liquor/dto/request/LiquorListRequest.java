package com.woowacamp.soolsool.core.liquor.dto.request;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;


@Getter
@RequiredArgsConstructor
public class LiquorListRequest {
    @Nullable
    private final LiquorBrewType brew;
    @Nullable
    private final LiquorRegionType region;
    @Nullable
    private final LiquorStatusType status;
    @Nullable
    private final String brand;
    @Nullable
    private final Long liquorId;
    @Nullable
    private final Long clickCount;
}
