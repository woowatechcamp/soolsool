package com.woowacamp.soolsool.core.statistics.service;

import com.woowacamp.soolsool.core.statistics.repository.StatisticsRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsService {

    private static final int SALES_UPDATE_DURATION = 7;
    private static final int CTR_UPDATE_DURATION = 1;

    private final StatisticsRepository statisticsRepository;

    @Transactional
    public void updateStatisticsSales() {
        LocalDate dateNow = LocalDate.now();
        LocalDate startDate = dateNow.minusDays(SALES_UPDATE_DURATION);
        log.info("startDate : {} | dateNow : {}", startDate, dateNow);

        statisticsRepository.updateStatisticsSales(startDate, dateNow);
    }

    @Transactional
    public void updateStatisticsCtr() {
        LocalDate dateNow = LocalDate.now();
        LocalDate startDate = dateNow.minusDays(CTR_UPDATE_DURATION);
        log.info("startDate : {} | dateNow : {}", startDate, dateNow);
        
    }
}
