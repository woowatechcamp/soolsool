package com.woowacamp.soolsool.core.statistics.controller;

import static com.woowacamp.soolsool.core.statistics.code.StatisticsResultCode.STATISTICS_TOP5_SALE_PRICE;
import static com.woowacamp.soolsool.core.statistics.code.StatisticsResultCode.STATISTICS_TOP5_SALE_QUANTITY;
import static org.springframework.http.HttpStatus.OK;

import com.woowacamp.soolsool.core.statistics.dto.response.LiquorSalePriceResponse;
import com.woowacamp.soolsool.core.statistics.dto.response.LiquorSaleQuantityResponse;
import com.woowacamp.soolsool.core.statistics.service.StatisticsService;
import com.woowacamp.soolsool.global.aop.RequestLogging;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.common.ApiResponse;
import java.util.List;
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

    @NoAuth
    @RequestLogging
    @GetMapping("/price")
    public ResponseEntity<ApiResponse<List<LiquorSalePriceResponse>>> findTop5SalePrice() {
        final List<LiquorSalePriceResponse> liquorSalePriceResponses
            = statisticsService.findTop5SalePrice();

        return ResponseEntity.status(OK)
            .body(ApiResponse.of(STATISTICS_TOP5_SALE_PRICE, liquorSalePriceResponses));
    }

    @NoAuth
    @RequestLogging
    @GetMapping("/quantity")
    public ResponseEntity<ApiResponse<List<LiquorSaleQuantityResponse>>> findTop5SaleQuantity() {
        final List<LiquorSaleQuantityResponse> liquorSaleQuantityResponses
            = statisticsService.findTop5SaleQuantity();

        return ResponseEntity.status(OK)
            .body(ApiResponse.of(STATISTICS_TOP5_SALE_QUANTITY, liquorSaleQuantityResponses));
    }
}
