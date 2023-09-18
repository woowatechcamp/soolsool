package com.woowacamp.soolsool.core.statistics.infra;

import com.woowacamp.soolsool.core.statistics.domain.StatisticLiquors;
import org.springframework.stereotype.Component;

@Component
public interface StatisticRedis {

    StatisticLiquors findTop5StatisticLiquorsBySalePrice();

    StatisticLiquors saveTop5StatisticLiquorsBySalePrice(
        final StatisticLiquors statisticLiquors);

    StatisticLiquors findTop5StatisticLiquorsBySaleQuantity();

    StatisticLiquors saveTop5StatisticLiquorsBySaleQuantity(
        final StatisticLiquors statisticLiquors);
}
