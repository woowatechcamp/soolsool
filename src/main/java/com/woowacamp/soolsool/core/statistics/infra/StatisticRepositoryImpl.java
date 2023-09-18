package com.woowacamp.soolsool.core.statistics.infra;

import com.woowacamp.soolsool.core.statistics.domain.StatisticLiquorImpl;
import com.woowacamp.soolsool.core.statistics.domain.StatisticLiquors;
import com.woowacamp.soolsool.core.statistics.repository.StatisticRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@RequiredArgsConstructor
@Component
@Slf4j
public class StatisticRepositoryImpl implements StatisticRepository {

    private static final int SALES_UPDATE_DURATION = 7;
    private static final int CTR_UPDATE_DURATION = 1;

    private final StatisticJpaRepository statisticJpaRepository;
    private final StatisticRedis statisticRedis;

    @Override
    public StatisticLiquors findTop5LiquorsBySalePrice() {
        final StatisticLiquors statisticLiquors
            = statisticRedis.findTop5StatisticLiquorsBySalePrice();

        if (!Objects.isNull(statisticLiquors)) {
            return statisticLiquors;
        }

        final List<StatisticLiquorImpl> top5LiquorAndSalePrices
            = statisticJpaRepository.findTop5LiquorsAndSalePrice().stream()
            .map(StatisticLiquorImpl::from)
            .collect(Collectors.toUnmodifiableList());
        return statisticRedis.saveTop5StatisticLiquorsBySalePrice(
            new StatisticLiquors(top5LiquorAndSalePrices));
    }

    @Override
    public StatisticLiquors findTop5LiquorsBySaleQuantity() {
        final StatisticLiquors statisticLiquors
            = statisticRedis.findTop5StatisticLiquorsBySaleQuantity();

        if (!Objects.isNull(statisticLiquors)) {
            return statisticLiquors;
        }

        final List<StatisticLiquorImpl> top5LiquorAnsSaleQuantities
            = statisticJpaRepository.findTop5LiquorsAndSaleQuantity().stream()
            .map(StatisticLiquorImpl::from)
            .collect(Collectors.toUnmodifiableList());
        return statisticRedis.saveTop5StatisticLiquorsBySaleQuantity(
            new StatisticLiquors(top5LiquorAnsSaleQuantities));
    }

    @Override
    public void updateStatistic() {
        final LocalDate dateNow = LocalDate.now();

        updateJpaStatisticsSales(dateNow);
        updateJpaStatisticsCtr(dateNow);

        saveRedisTop5StatisticsLiquorsBySalePrice();
        saveRedisTop5StatisticsLiquorsBySaleQuantity();
    }

    private void updateJpaStatisticsSales(final LocalDate dateNow) {
        final LocalDate startDate = dateNow.minusDays(SALES_UPDATE_DURATION);
        log.info("통계 시작일 : {}, 통계 종료일 : {}", startDate, dateNow);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        statisticJpaRepository.updateStatisticsSales(startDate, dateNow);
        stopWatch.stop();

        final double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("판매량, 판매금액 자동 통계 집계 쿼리 실행 걸린 총 시간 : {}", totalTimeSeconds);
    }

    private void updateJpaStatisticsCtr(final LocalDate dateNow) {
        final LocalDate startDate = dateNow.minusDays(CTR_UPDATE_DURATION);
        log.info("클릭 통계 집계일 : {}", startDate);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        statisticJpaRepository.updateStatisticsCtr(startDate);
        stopWatch.stop();

        final double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("노출수, 클릭수 자동 통계 집계 쿼리 실행 걸린 총 시간 : {}", totalTimeSeconds);
    }

    private void saveRedisTop5StatisticsLiquorsBySalePrice() {
        final List<StatisticLiquorImpl> statisticsLiquors
            = statisticJpaRepository.findTop5LiquorsAndSalePrice().stream()
            .map(StatisticLiquorImpl::from)
            .collect(Collectors.toUnmodifiableList());

        statisticRedis.saveTop5StatisticLiquorsBySalePrice(
            new StatisticLiquors(statisticsLiquors));
    }

    private void saveRedisTop5StatisticsLiquorsBySaleQuantity() {
        final List<StatisticLiquorImpl> statisticsLiquors
            = statisticJpaRepository.findTop5LiquorsAndSaleQuantity().stream()
            .map(StatisticLiquorImpl::from)
            .collect(Collectors.toUnmodifiableList());

        statisticRedis.saveTop5StatisticLiquorsBySaleQuantity(
            new StatisticLiquors(statisticsLiquors));
    }
}
