package com.woowacamp.soolsool.core.statistics.dto.response;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LiquorSaleQuantityResponse {

    private Long id;
    private String name;
    private String brew;
    private String region;
    private String imageUrl;
    private Long totalSaleQuantity;

    public static LiquorSaleQuantityResponse from(
        final Liquor liquor,
        final Long totalSaleQuantity
    ) {
        return new LiquorSaleQuantityResponse(
            liquor.getId(),
            liquor.getName(),
            liquor.getBrew().getType().getName(),
            liquor.getRegion().getType().getName(),
            liquor.getImageUrl(),
            totalSaleQuantity
        );
    }
}
