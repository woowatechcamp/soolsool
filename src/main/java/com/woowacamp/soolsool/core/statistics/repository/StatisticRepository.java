package com.woowacamp.soolsool.core.statistics.repository;

import com.woowacamp.soolsool.core.statistics.domain.StatisticLiquors;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository {

    StatisticLiquors findTop5LiquorsBySalePrice();

    StatisticLiquors findTop5LiquorsBySaleQuantity();

    void updateStatistic();
}
