package com.woowacamp.soolsool.core.liquor.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import lombok.Getter;

@Getter
public class LiquorClickElementResponse {

    private final Long id;
    private final String name;
    private final String price;
    private final String imageUrl;
    private final Integer stock;
    private final Long clickCount;

    @JsonCreator
    public LiquorClickElementResponse(final Long id, final String name, final String price,
        final String imageUrl, final Integer stock, final Long clickCount
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.clickCount = clickCount;
    }

    public LiquorClickElementResponse(final Liquor liquor, final LiquorCtrClick clickCount) {
        this.id = liquor.getId();
        this.name = liquor.getName();
        this.price = liquor.getPrice().toString();
        this.imageUrl = liquor.getImageUrl();
        this.stock = liquor.getTotalStock();
        this.clickCount = clickCount.getCount();
    }

    public static LiquorClickElementResponse from(final Liquor liquor) {
        return new LiquorClickElementResponse(
            liquor.getId(),
            liquor.getName(),
            liquor.getPrice().toString(),
            liquor.getImageUrl(),
            liquor.getTotalStock(),
            0L
        );
    }
    public static LiquorClickElementResponse of(final Liquor liquor, final Long clickCount) {
        return new LiquorClickElementResponse(
            liquor.getId(),
            liquor.getName(),
            liquor.getPrice().toString(),
            liquor.getImageUrl(),
            liquor.getTotalStock(),
            clickCount
        );
    }
}
