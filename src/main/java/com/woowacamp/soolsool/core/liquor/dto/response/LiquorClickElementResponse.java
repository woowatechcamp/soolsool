package com.woowacamp.soolsool.core.liquor.dto.response;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LiquorClickElementResponse {

    private final Long id;
    private final String name;
    private final String price;
    private final String imageUrl;
    private final Integer stock;
    private final Long clickCount;

    public LiquorClickElementResponse(final Liquor liquor, final LiquorCtrClick clickCount) {
        this(
            liquor.getId(),
            liquor.getName(),
            liquor.getPrice().toString(),
            liquor.getImageUrl(),
            liquor.getTotalStock(),
            clickCount.getCount()
        );
    }
}
