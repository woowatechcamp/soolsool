package com.woowacamp.soolsool.core.statistics.infra;

import com.woowacamp.soolsool.core.statistics.domain.StatisticLiquors;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class StatisticRedisImpl implements StatisticRedis {

    private static final String MAP_KEY_STATISTICS = "statistics";
    private static final String STATISTICS_PRICE = "top5SalePrice";
    private static final String STATISTICS_QUANTITY = "top5SaleQuantity";

    private final RMapCache<String, StatisticLiquors> rCacheStatistics;

    public StatisticRedisImpl(final RedissonClient redissonClient) {
        rCacheStatistics = redissonClient.getMapCache(MAP_KEY_STATISTICS);
    }

    @Override
    public StatisticLiquors findTop5StatisticLiquorsBySalePrice() {
        return rCacheStatistics.get(STATISTICS_PRICE);
    }

    @Override
    public StatisticLiquors saveTop5StatisticLiquorsBySalePrice(
        final StatisticLiquors statisticLiquors
    ) {
        rCacheStatistics.put(STATISTICS_PRICE, statisticLiquors);
        return statisticLiquors;
    }

    @Override
    public StatisticLiquors findTop5StatisticLiquorsBySaleQuantity() {
        return rCacheStatistics.get(STATISTICS_QUANTITY);
    }

    @Override
    public StatisticLiquors saveTop5StatisticLiquorsBySaleQuantity(
        final StatisticLiquors statisticLiquors
    ) {
        rCacheStatistics.put(STATISTICS_QUANTITY, statisticLiquors);
        return statisticLiquors;
    }
}
