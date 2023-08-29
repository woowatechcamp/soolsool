package com.woowacamp.soolsool.core.statistics.service;

import com.woowacamp.soolsool.core.statistics.repository.StatisticsRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsService {

    private static final int SALES_UPDATE_DURATION = 7;
    private static final int CTR_UPDATE_DURATION = 1;

    private final StatisticsRepository statisticsRepository;

    @Transactional
    public void updateStatistics() {
        LocalDate dateNow = LocalDate.now();

        updateStatisticsSales(dateNow);
        updateStatisticsCtr(dateNow);
    }

    public void updateStatisticsSales(final LocalDate dateNow) {
        LocalDate startDate = dateNow.minusDays(SALES_UPDATE_DURATION);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        statisticsRepository.updateStatisticsSales(startDate, dateNow);
        stopWatch.stop();

        final double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("판매량, 판매금액 자동 통계 집계 쿼리 실행 걸린 총 시간 : {}", totalTimeSeconds);
    }

    public void updateStatisticsCtr(final LocalDate dateNow) {
        LocalDate startDate = dateNow.minusDays(CTR_UPDATE_DURATION);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        statisticsRepository.updateStatisticsCtr(startDate);
        stopWatch.stop();

        final double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("노출수, 클릭수 자동 통계 집계 쿼리 실행 걸린 총 시간 : {}", totalTimeSeconds);
    }
}
