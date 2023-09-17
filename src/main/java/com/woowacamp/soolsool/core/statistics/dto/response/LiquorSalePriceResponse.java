package com.woowacamp.soolsool.core.statistics.dto.response;

import com.woowacamp.soolsool.core.statistics.domain.StatisticLiquor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LiquorSalePriceResponse {

    private final Long id;
    private final String name;
    private final String brand;
    private final String imageUrl;
    private final Long price;
    private final Long accumulatedSalePrice;

    public static LiquorSalePriceResponse from(
        @NonNull final StatisticLiquor statisticLiquor
    ) {
        return new LiquorSalePriceResponse(
            statisticLiquor.getLiquorId(),
            statisticLiquor.getLiquorName(),
            statisticLiquor.getLiquorBrand(),
            statisticLiquor.getLiquorImageUrl(),
            statisticLiquor.getLiquorPrice(),
            statisticLiquor.getLiquorValue()
        );
    }
}
