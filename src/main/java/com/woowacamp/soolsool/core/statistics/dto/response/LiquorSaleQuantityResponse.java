package com.woowacamp.soolsool.core.statistics.dto.response;

import com.woowacamp.soolsool.core.statistics.domain.StatisticLiquor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LiquorSaleQuantityResponse {

    private final Long id;
    private final String name;
    private final String brand;
    private final String imageUrl;
    private final Long price;
    private final Long accumulatedSaleQuantity;

    public static LiquorSaleQuantityResponse from(
        @NonNull final StatisticLiquor statisticLiquor
    ) {
        return new LiquorSaleQuantityResponse(
            statisticLiquor.getLiquorId(),
            statisticLiquor.getLiquorName(),
            statisticLiquor.getLiquorBrand(),
            statisticLiquor.getLiquorImageUrl(),
            statisticLiquor.getLiquorPrice(),
            statisticLiquor.getLiquorValue()
        );
    }
}
