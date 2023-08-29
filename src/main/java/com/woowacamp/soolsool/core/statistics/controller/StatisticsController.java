package com.woowacamp.soolsool.core.statistics.controller;

import com.woowacamp.soolsool.core.statistics.service.StatisticsService;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @NoAuth
    @PutMapping("/sales")
    @Scheduled(cron = "0 0 0 * * *")
    public ResponseEntity<ApiResponse<Void>> updateStatisticsSales() {
        log.info("updateStatisticsSales 자동 통계 집계 쿼리 실행");
        statisticsService.updateStatisticsSales();

        return ResponseEntity.ok().build();
    }

    @NoAuth
    @PostMapping("/ctr")
    @Scheduled(cron = "0 0 0 * * *")
    public ResponseEntity<ApiResponse<Void>> updateStatisticsCtr() {
        log.info("updateStatisticsCtr 자동 통계 집계 쿼리 실행");
        statisticsService.updateStatisticsCtr();

        return ResponseEntity.ok().build();
    }
}
