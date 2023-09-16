package com.woowacamp.soolsool.core.statistics.infra;

import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquor;
import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquors;
import com.woowacamp.soolsool.core.statistics.repository.StatisticsRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@RequiredArgsConstructor
@Component
@Slf4j
public class StatisticsRepositoryImpl implements StatisticsRepository {

    private static final int SALES_UPDATE_DURATION = 7;
    private static final int CTR_UPDATE_DURATION = 1;

    private final StatisticsJpaRepository statisticsJpaRepository;
    private final StatisticsRedis statisticsRedis;

    @Override
    public StatisticsLiquors findTop5LiquorIdAndSalePrice() {
        final Optional<StatisticsLiquors> statisticsLiquors = statisticsRedis.findTop5StatisticsLiquorsBySalePrice();

        if (statisticsLiquors.isPresent()) {
            return statisticsLiquors.get();
        }

        final List<StatisticsLiquor> top5LiquorIdAnsSalePrices = statisticsJpaRepository.findTop5LiquorIdAndSalePrice();
        return statisticsRedis.saveTop5StatisticsLiquorsBySalePrice(
            StatisticsLiquors.from(top5LiquorIdAnsSalePrices)
        );
    }

    @Override
    public StatisticsLiquors findTop5LiquorIdAndSaleQuantity() {
        final Optional<StatisticsLiquors> statisticsLiquors = statisticsRedis.findTop5StatisticsLiquorsBySaleQuantity();

        if (statisticsLiquors.isPresent()) {
            return statisticsLiquors.get();
        }

        final List<StatisticsLiquor> top5LiquorIdAnsSaleQuantities = statisticsJpaRepository.findTop5LiquorIdAndSaleQuantity();
        return statisticsRedis.saveTop5StatisticsLiquorsBySaleQuantity(
            StatisticsLiquors.from(top5LiquorIdAnsSaleQuantities)
        );
    }

    @Override
    public void updateStatistics() {
        final LocalDate dateNow = LocalDate.now();

        updateJpaStatisticsSales(dateNow);
        updateJpaStatisticsCtr(dateNow);

        saveRedisTop5StatisticsLiquorsBySalePrice();
        saveRedisTop5StatisticsLiquorsBySaleQuantity();
    }

    private void updateJpaStatisticsSales(final LocalDate dateNow) {
        LocalDate startDate = dateNow.minusDays(SALES_UPDATE_DURATION);
        log.info("통계 시작일 : {}, 통계 종료일 : {}", startDate, dateNow);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        statisticsJpaRepository.updateStatisticsSales(startDate, dateNow);
        stopWatch.stop();

        final double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("판매량, 판매금액 자동 통계 집계 쿼리 실행 걸린 총 시간 : {}", totalTimeSeconds);
    }

    private void updateJpaStatisticsCtr(final LocalDate dateNow) {
        LocalDate startDate = dateNow.minusDays(CTR_UPDATE_DURATION);
        log.info("클릭 통계 집계일 : {}", startDate);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        statisticsJpaRepository.updateStatisticsCtr(startDate);
        stopWatch.stop();

        final double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("노출수, 클릭수 자동 통계 집계 쿼리 실행 걸린 총 시간 : {}", totalTimeSeconds);
    }

    private void saveRedisTop5StatisticsLiquorsBySalePrice() {
        final List<StatisticsLiquor> top5LiquorIdAnsSalePrices = statisticsJpaRepository.findTop5LiquorIdAndSalePrice();
        final StatisticsLiquors statisticsLiquors = statisticsRedis.saveTop5StatisticsLiquorsBySalePrice(
            StatisticsLiquors.from(top5LiquorIdAnsSalePrices)
        );

        statisticsRedis.saveTop5StatisticsLiquorsBySalePrice(statisticsLiquors);
    }

    private void saveRedisTop5StatisticsLiquorsBySaleQuantity() {
        final List<StatisticsLiquor> top5LiquorIdAnsSaleQuantities = statisticsJpaRepository.findTop5LiquorIdAndSaleQuantity();
        final StatisticsLiquors statisticsLiquors = statisticsRedis.saveTop5StatisticsLiquorsBySalePrice(
            StatisticsLiquors.from(top5LiquorIdAnsSaleQuantities)
        );

        statisticsRedis.saveTop5StatisticsLiquorsBySaleQuantity(statisticsLiquors);
    }
}
