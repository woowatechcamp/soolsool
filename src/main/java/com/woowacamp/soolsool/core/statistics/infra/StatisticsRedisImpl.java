package com.woowacamp.soolsool.core.statistics.infra;

import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquors;
import java.util.Optional;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class StatisticsRedisImpl implements StatisticsRedis {

    private static final String MAP_KEY_STATISTICS = "statistics";
    private static final String STATISTICS_PRICE = "top5SalePrice";
    private static final String STATISTICS_QUANTITY = "top5SaleQuantity";

    private final RMapCache<String, StatisticsLiquors> rCacheStatistics;

    public StatisticsRedisImpl(final RedissonClient redissonClient) {
        rCacheStatistics = redissonClient.getMapCache(MAP_KEY_STATISTICS);
    }

    @Override
    public Optional<StatisticsLiquors> findTop5StatisticsLiquorsBySalePrice() {
        return Optional.of(rCacheStatistics.get(STATISTICS_PRICE));
    }

    @Override
    public StatisticsLiquors saveTop5StatisticsLiquorsBySalePrice(
        final StatisticsLiquors statisticsLiquors) {
        rCacheStatistics.put(STATISTICS_PRICE, statisticsLiquors);
        return statisticsLiquors;
    }

    @Override
    public Optional<StatisticsLiquors> findTop5StatisticsLiquorsBySaleQuantity() {
        return Optional.of(rCacheStatistics.get(STATISTICS_QUANTITY));
    }

    @Override
    public StatisticsLiquors saveTop5StatisticsLiquorsBySaleQuantity(
        final StatisticsLiquors statisticsLiquors
    ) {
        rCacheStatistics.put(STATISTICS_QUANTITY, statisticsLiquors);
        return statisticsLiquors;
    }
}
