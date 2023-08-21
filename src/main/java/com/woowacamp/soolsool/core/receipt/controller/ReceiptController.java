package com.woowacamp.soolsool.core.receipt.controller;

import static com.woowacamp.soolsool.core.receipt.code.ReceiptResultCode.RECEIPT_ADD_SUCCESS;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptResultCode.RECEIPT_LIST_DELETED;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptResultCode.RECEIPT_MODIFY_STATUS_SUCCESS;

import com.woowacamp.soolsool.core.receipt.dto.ReceiptModifyRequest;
import com.woowacamp.soolsool.core.receipt.dto.ReceiptResponse;
import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.common.ApiResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        final Long receiptId = receiptService.addReceipt(memberId);

        return ResponseEntity.created(URI.create("/receipts/" + receiptId))
            .body(ApiResponse.from(RECEIPT_ADD_SUCCESS));
    }

    @GetMapping("/{receiptId}")
    public ResponseEntity<ApiResponse<ReceiptResponse>> receiptDetails(
        @LoginUser final Long memberId,
        @PathVariable final Long receiptId
    ) {
        final ReceiptResponse receipt = receiptService.findReceipt(memberId, receiptId);

        return ResponseEntity.ok(ApiResponse.of(RECEIPT_LIST_DELETED, receipt));
    }

    @PatchMapping("/{receiptId}")
    public ResponseEntity<ApiResponse<Void>> modifyReceiptStatus(@LoginUser final Long memberId,
        @PathVariable final Long receiptId,
        @RequestBody final ReceiptModifyRequest receiptModifyRequest
    ) {
        receiptService.modifyReceiptStatus(memberId, receiptId, receiptModifyRequest);
        return ResponseEntity.ok(ApiResponse.from(RECEIPT_MODIFY_STATUS_SUCCESS));
    }
}
