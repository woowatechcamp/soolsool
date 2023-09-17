package com.woowacamp.soolsool.core.liquor.controller;

import com.woowacamp.soolsool.core.liquor.dto.liquorStock.LiquorStockSaveRequest;
import com.woowacamp.soolsool.core.liquor.service.LiquorStockService;
import com.woowacamp.soolsool.global.aop.RequestLogging;
import com.woowacamp.soolsool.global.auth.dto.Vendor;
import com.woowacamp.soolsool.global.common.ApiResponse;
import com.woowacamp.soolsool.global.common.LiquorResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/liquor-stocks")
@RequiredArgsConstructor
public class LiquorStockController {

    private final LiquorStockService liquorStockService;

    @Vendor
    @RequestLogging
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> saveLiquorStock(
        @RequestBody final LiquorStockSaveRequest request
    ) {
        liquorStockService.saveLiquorStock(request);

        return ResponseEntity.ok(ApiResponse.from(LiquorResultCode.LIQUOR_STOCK_SAVED));
    }
}
