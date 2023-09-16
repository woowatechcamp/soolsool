package com.woowacamp.soolsool.core.statistics.infra;

import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquors;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public interface StatisticsRedis {

    Optional<StatisticsLiquors> findTop5StatisticsLiquorsBySalePrice();

    StatisticsLiquors saveTop5StatisticsLiquorsBySalePrice(
        final StatisticsLiquors statisticsLiquors);

    Optional<StatisticsLiquors> findTop5StatisticsLiquorsBySaleQuantity();

    StatisticsLiquors saveTop5StatisticsLiquorsBySaleQuantity(
        final StatisticsLiquors statisticsLiquors);
}
