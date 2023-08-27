package com.woowacamp.soolsool.core.liquor.dto;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LiquorElementResponse {

    private final Long id;
    private final String name;
    private final String price;
    private final String imageUrl;
    private final Integer stock;

    public LiquorElementResponse(final Liquor liquor) {
        this.id = liquor.getId();
        this.name = liquor.getName();
        this.price = liquor.getPrice().toString();
        this.imageUrl = liquor.getImageUrl();
        this.stock = liquor.getTotalStock();
    }

    public static LiquorElementResponse from(final Liquor liquor) {
        return new LiquorElementResponse(
            liquor.getId(),
            liquor.getName(),
            liquor.getPrice().toString(),
            liquor.getImageUrl(),
            liquor.getTotalStock()
        );
    }
}
