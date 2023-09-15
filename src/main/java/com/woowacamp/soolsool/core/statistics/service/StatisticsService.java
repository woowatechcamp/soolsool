package com.woowacamp.soolsool.core.statistics.service;

import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquors;
import com.woowacamp.soolsool.core.statistics.dto.response.LiquorSalePriceResponse;
import com.woowacamp.soolsool.core.statistics.dto.response.LiquorSaleQuantityResponse;
import com.woowacamp.soolsool.core.statistics.repository.StatisticsRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
    private final LiquorRepository liquorRepository;

    @Transactional(readOnly = true)
    public List<LiquorSalePriceResponse> findTop5SalePrice() {
        StatisticsLiquors top5LiquorByPrices = statisticsRepository.rFindTop5LiquorBySalePrice();

        return liquorRepository.findAllById(top5LiquorByPrices.getIds()).stream()
            .map(liquor ->
                LiquorSalePriceResponse.from(liquor, top5LiquorByPrices.getValue(liquor.getId())))
            .sorted(Comparator.comparingLong(LiquorSalePriceResponse::getTotalSalePrice).reversed())
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<LiquorSaleQuantityResponse> findTop5SaleQuantity() {
        StatisticsLiquors top5LiquorByQuantities = statisticsRepository.rFindTop5LiquorBySaleQuantity();

        return liquorRepository.findAllById(top5LiquorByQuantities.getIds()).stream()
            .map(liquor -> LiquorSaleQuantityResponse
                .from(liquor, top5LiquorByQuantities.getValue(liquor.getId())))
            .sorted(Comparator
                .comparingLong(LiquorSaleQuantityResponse::getTotalSaleQuantity).reversed())
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateStatistics() {
        LocalDate dateNow = LocalDate.now();

        updateStatisticsSales(dateNow);
        updateStatisticsCtr(dateNow);

        rUpdateCacheStatistics();
    }

    private void updateStatisticsSales(final LocalDate dateNow) {
        LocalDate startDate = dateNow.minusDays(SALES_UPDATE_DURATION);
        log.info("통계 시작일 : {}, 통계 종료일 : {}", startDate, dateNow);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        statisticsRepository.updateStatisticsSales(startDate, dateNow);
        stopWatch.stop();

        final double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("판매량, 판매금액 자동 통계 집계 쿼리 실행 걸린 총 시간 : {}", totalTimeSeconds);
    }

    private void updateStatisticsCtr(final LocalDate dateNow) {
        LocalDate startDate = dateNow.minusDays(CTR_UPDATE_DURATION);
        log.info("클릭 통계 집계일 : {}", startDate);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        statisticsRepository.updateStatisticsCtr(startDate);
        stopWatch.stop();

        final double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("노출수, 클릭수 자동 통계 집계 쿼리 실행 걸린 총 시간 : {}", totalTimeSeconds);
    }

    private void rUpdateCacheStatistics() {
        statisticsRepository.rClearStatistics();
        statisticsRepository.rFindTop5LiquorBySalePrice();
        statisticsRepository.rFindTop5LiquorBySaleQuantity();
    }
}
