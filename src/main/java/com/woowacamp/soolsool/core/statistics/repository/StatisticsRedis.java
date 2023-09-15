package com.woowacamp.soolsool.core.statistics.repository;

import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquors;
import org.springframework.stereotype.Component;

@Component
public interface StatisticsRedis {

    StatisticsLiquors rFindTop5LiquorBySalePrice();

    StatisticsLiquors rFindTop5LiquorBySaleQuantity();
}
