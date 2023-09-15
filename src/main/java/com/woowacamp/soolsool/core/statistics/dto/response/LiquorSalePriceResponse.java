package com.woowacamp.soolsool.core.statistics.dto.response;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LiquorSalePriceResponse {

    private Long id;
    private String name;
    private String brew;
    private String region;
    private String imageUrl;
    private Long totalSalePrice;

    public static LiquorSalePriceResponse from(final Liquor liquor, final Long totalSalePrice) {
        return new LiquorSalePriceResponse(
            liquor.getId(),
            liquor.getName(),
            liquor.getBrew().getType().getName(),
            liquor.getRegion().getType().getName(),
            liquor.getImageUrl(),
            totalSalePrice
        );
    }
}
