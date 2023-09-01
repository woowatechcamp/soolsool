package com.woowacamp.soolsool.core.statistics.controller;

import com.woowacamp.soolsool.core.statistics.service.StatisticsService;
import com.woowacamp.soolsool.global.aop.RequestLogging;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @NoAuth
    @RequestLogging
    @GetMapping
    @Scheduled(cron = "0 0 0 * * *")
    public ResponseEntity<ApiResponse<Void>> updateStatisticsSales() {
        statisticsService.updateStatistics();

        return ResponseEntity.ok().build();
    }
}
