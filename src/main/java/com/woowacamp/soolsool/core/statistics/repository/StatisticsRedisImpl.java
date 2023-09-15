package com.woowacamp.soolsool.core.statistics.repository;

import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquors;
import java.util.Objects;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class StatisticsRedisImpl implements StatisticsRedis {

    private static final String MAP_KEY_STATISTICS = "statistics";
    private static final String STATISTICS_PRICE = "top5SalePrice";
    private static final String STATISTICS_QUANTITY = "top5SaleQuantity";

    private final RMapCache<String, StatisticsLiquors> rCacheStatistics;
    private final StatisticsJpaRepository statisticsJpaRepository;

    public StatisticsRedisImpl(
        final RedissonClient redissonClient,
        final StatisticsJpaRepository statisticsJpaRepository
    ) {
        rCacheStatistics = redissonClient.getMapCache(MAP_KEY_STATISTICS);
        this.statisticsJpaRepository = statisticsJpaRepository;
    }

    @Override
    public StatisticsLiquors rFindTop5LiquorBySalePrice() {
        StatisticsLiquors top5LiquorIds = rCacheStatistics.get(STATISTICS_PRICE);

        if (Objects.isNull(top5LiquorIds)) {
            top5LiquorIds = StatisticsLiquors
                .from(statisticsJpaRepository.findTop5LiquorIdBySalePrice());

            rCacheStatistics.put(STATISTICS_PRICE, top5LiquorIds);
        }

        return top5LiquorIds;
    }

    @Override
    public StatisticsLiquors rFindTop5LiquorBySaleQuantity() {
        StatisticsLiquors top5LiquorIds = rCacheStatistics.get(STATISTICS_QUANTITY);

        if (Objects.isNull(top5LiquorIds)) {
            top5LiquorIds = StatisticsLiquors
                .from(statisticsJpaRepository.findTop5LiquorIdBySaleQuantity());

            rCacheStatistics.put(STATISTICS_QUANTITY, top5LiquorIds);
        }

        return top5LiquorIds;
    }

    @Override
    public void rClearStatistics() {
        rCacheStatistics.clear();
    }
}
