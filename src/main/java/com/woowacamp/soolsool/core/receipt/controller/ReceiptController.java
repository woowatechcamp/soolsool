package com.woowacamp.soolsool.core.receipt.controller;

import static com.woowacamp.soolsool.core.receipt.code.ReceiptResultCode.RECEIPT_ADD_SUCCESS;

import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addReceipt(
        @LoginUser final Long memberId
    ) {
        receiptService.addReceipt(memberId);
        return ResponseEntity.ok(ApiResponse.from(RECEIPT_ADD_SUCCESS));
    }
}
