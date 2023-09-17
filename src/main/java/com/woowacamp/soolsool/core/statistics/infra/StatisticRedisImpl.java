package com.woowacamp.soolsool.core.statistics.infra;

import com.woowacamp.soolsool.core.statistics.domain.StatisticLiquors;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class StatisticRedisImpl implements StatisticRedis {

    private static final String KEY_STATISTICS = "statistic_liquors";
    private static final String FIELD_TOP5_PRICE = "top5_sale_price";
    private static final String FIELD_TOP5_QUANTITY = "top5_sale_quantity";

    private final RMapCache<String, StatisticLiquors> rCacheStatistics;

    public StatisticRedisImpl(final RedissonClient redissonClient) {
        rCacheStatistics = redissonClient.getMapCache(KEY_STATISTICS);
    }

    @Override
    public StatisticLiquors findTop5StatisticLiquorsBySalePrice() {
        return rCacheStatistics.get(FIELD_TOP5_PRICE);
    }

    @Override
    public StatisticLiquors saveTop5StatisticLiquorsBySalePrice(
        final StatisticLiquors statisticLiquors
    ) {
        rCacheStatistics.put(FIELD_TOP5_PRICE, statisticLiquors);
        return statisticLiquors;
    }

    @Override
    public StatisticLiquors findTop5StatisticLiquorsBySaleQuantity() {
        return rCacheStatistics.get(FIELD_TOP5_QUANTITY);
    }

    @Override
    public StatisticLiquors saveTop5StatisticLiquorsBySaleQuantity(
        final StatisticLiquors statisticLiquors
    ) {
        rCacheStatistics.put(FIELD_TOP5_QUANTITY, statisticLiquors);
        return statisticLiquors;
    }
}
