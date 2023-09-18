package com.woowacamp.soolsool.core.statistics.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticLiquorImpl implements StatisticLiquor {

    private final Long liquorId;
    private final String liquorName;
    private final String liquorBrand;
    private final String liquorImageUrl;
    private final Long liquorPrice;
    private final Long liquorValue;

    public static StatisticLiquorImpl from(final StatisticLiquor statisticLiquor) {
        return new StatisticLiquorImpl(
            statisticLiquor.getLiquorId(),
            statisticLiquor.getLiquorName(),
            statisticLiquor.getLiquorBrand(),
            statisticLiquor.getLiquorImageUrl(),
            statisticLiquor.getLiquorPrice(),
            statisticLiquor.getLiquorValue()
        );
    }

    @Override
    public Long getLiquorId() {
        return this.liquorId;
    }

    @Override
    public String getLiquorName() {
        return this.liquorName;
    }

    @Override
    public String getLiquorBrand() {
        return this.liquorBrand;
    }

    @Override
    public String getLiquorImageUrl() {
        return this.liquorImageUrl;
    }

    @Override
    public Long getLiquorPrice() {
        return this.liquorPrice;
    }

    @Override
    public Long getLiquorValue() {
        return this.liquorValue;
    }
}
