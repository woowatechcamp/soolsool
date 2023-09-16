package com.woowacamp.soolsool.core.statistics.repository;

import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquors;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository {

    StatisticsLiquors findTop5LiquorIdAndSalePrice();

    StatisticsLiquors findTop5LiquorIdAndSaleQuantity();

    void updateStatistics();
}
