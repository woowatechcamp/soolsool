package com.woowacamp.soolsool.core.receipt.controller;

import static com.woowacamp.soolsool.core.receipt.code.ReceiptResultCode.RECEIPT_ADD_SUCCESS;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptResultCode.RECEIPT_FOUND;

import com.woowacamp.soolsool.core.receipt.dto.response.ReceiptResponse;
import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
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

    private static final String DEFAULT_URL = "/receipts";

    private final ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> addReceipt(
        @LoginUser final Long memberId
    ) {
        log.info("POST {} | memberId : {}",
            DEFAULT_URL, memberId);

        final Long receiptId = receiptService.addReceipt(memberId);

        return ResponseEntity.created(URI.create("/receipts/" + receiptId))
            .body(ApiResponse.of(RECEIPT_ADD_SUCCESS, receiptId));
    }

    @GetMapping("/{receiptId}")
    public ResponseEntity<ApiResponse<ReceiptResponse>> receiptDetails(
        @LoginUser final Long memberId,
        @PathVariable final Long receiptId
    ) {
        log.info("GET {}/{} | memberId : {}",
            DEFAULT_URL, receiptId, memberId);

        final ReceiptResponse receipt = receiptService.findReceipt(memberId, receiptId);

        return ResponseEntity.ok(ApiResponse.of(RECEIPT_FOUND, receipt));
    }
}
