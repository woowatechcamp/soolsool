package com.woowacamp.soolsool.core.statistics.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsLiquorImpl implements StatisticsLiquor {

    private final Long liquorId;
    private final String liquorName;
    private final String liquorBrand;
    private final String liquorImageUrl;
    private final Long liquorPrice;
    private final Long liquorValue;

    public static StatisticsLiquorImpl from(final StatisticsLiquor statisticsLiquor) {
        return new StatisticsLiquorImpl(
            statisticsLiquor.getLiquorId(),
            statisticsLiquor.getLiquorName(),
            statisticsLiquor.getLiquorBrand(),
            statisticsLiquor.getLiquorImageUrl(),
            statisticsLiquor.getLiquorPrice(),
            statisticsLiquor.getLiquorValue()
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
