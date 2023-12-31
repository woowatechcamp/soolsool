package com.woowacamp.soolsool.core.receipt.controller;

import static com.woowacamp.soolsool.core.receipt.code.ReceiptResultCode.RECEIPT_ADD_SUCCESS;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptResultCode.RECEIPT_FOUND;

import com.woowacamp.soolsool.core.receipt.dto.response.ReceiptDetailResponse;
import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
import com.woowacamp.soolsool.global.aop.RequestLogging;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.common.ApiResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    @RequestLogging
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> addReceipt(
        @LoginUser final Long memberId
    ) {
        final Long receiptId = receiptService.addReceipt(memberId);

        return ResponseEntity.created(URI.create("/receipts/" + receiptId))
            .body(ApiResponse.of(RECEIPT_ADD_SUCCESS, receiptId));
    }

    @RequestLogging
    @GetMapping("/{receiptId}")
    public ResponseEntity<ApiResponse<ReceiptDetailResponse>> receiptDetails(
        @LoginUser final Long memberId,
        @PathVariable final Long receiptId
    ) {
        final ReceiptDetailResponse receipt = receiptService.findReceipt(memberId, receiptId);

        return ResponseEntity.ok(ApiResponse.of(RECEIPT_FOUND, receipt));
    }
}
