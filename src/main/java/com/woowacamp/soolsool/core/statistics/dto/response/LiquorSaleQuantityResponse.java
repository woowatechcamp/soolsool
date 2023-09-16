package com.woowacamp.soolsool.core.statistics.dto.response;

import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LiquorSaleQuantityResponse {

    private Long id;
    private String name;
    private String brand;
    private String imageUrl;
    private Long price;
    private Long accumulatedSaleQuantity;

    public static LiquorSaleQuantityResponse from(final StatisticsLiquor statisticsLiquor) {
        return new LiquorSaleQuantityResponse(
            statisticsLiquor.getLiquorId(),
            statisticsLiquor.getLiquorName(),
            statisticsLiquor.getLiquorBrand(),
            statisticsLiquor.getLiquorImageUrl(),
            statisticsLiquor.getLiquorPrice(),
            statisticsLiquor.getLiquorValue()
        );
    }
}
