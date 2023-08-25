package com.woowacamp.soolsool.core.payment.controller;

import static com.woowacamp.soolsool.core.payment.code.PayResultCode.PAY_APPROVE_SUCCESS;
import static com.woowacamp.soolsool.core.payment.code.PayResultCode.PAY_READY_SUCCESS;

import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.dto.response.PaySuccessResponse;
import com.woowacamp.soolsool.core.payment.service.PayService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    @PostMapping("/ready")
    public ResponseEntity<ApiResponse<PayReadyResponse>> payReady(
        @LoginUser final Long memberId,
        @RequestBody final PayOrderRequest payOrderRequest
    ) {
        return ResponseEntity.ok(
            ApiResponse.of(PAY_READY_SUCCESS, payService.ready(memberId, payOrderRequest)));
    }

    @GetMapping("/success/{receiptId}")
    public ResponseEntity<ApiResponse<PaySuccessResponse>> kakaoPaySuccess(
        @LoginUser final Long memberId,
        @RequestParam("pg_token") final String pgToken,
        @PathVariable("receiptId") final Long receiptId
    ) {
        final Long orderId = payService.approve(memberId, receiptId, pgToken);

        return ResponseEntity.ok(
            ApiResponse.of(PAY_APPROVE_SUCCESS, new PaySuccessResponse(orderId)));
    }
}
