package com.woowacamp.soolsool.core.liquor.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import lombok.Getter;

@Getter
public class LiquorElementResponse {

    private final Long id;
    private final String name;
    private final String price;
    private final String imageUrl;
    private final Integer stock;
    private final Long clickCount;

    @JsonCreator
    public LiquorElementResponse(final Long id, final String name, final String price,
        final String imageUrl, final Integer stock, final Long clickCount
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.clickCount = clickCount;
    }

    public LiquorElementResponse(final Liquor liquor, final LiquorCtrClick clickCount) {
        this.id = liquor.getId();
        this.name = liquor.getName();
        this.price = liquor.getPrice().toString();
        this.imageUrl = liquor.getImageUrl();
        this.stock = liquor.getTotalStock();
        this.clickCount = clickCount.getClick();
    }

    public static LiquorElementResponse from(final Liquor liquor) {
        return new LiquorElementResponse(
            liquor.getId(),
            liquor.getName(),
            liquor.getPrice().toString(),
            liquor.getImageUrl(),
            liquor.getTotalStock(),
            0L
        );
    }
    public static LiquorElementResponse of(final Liquor liquor, final Long clickCount) {
        return new LiquorElementResponse(
            liquor.getId(),
            liquor.getName(),
            liquor.getPrice().toString(),
            liquor.getImageUrl(),
            liquor.getTotalStock(),
            clickCount
        );
    }
}
