package com.woowacamp.soolsool.core.payment.controller;

import static com.woowacamp.soolsool.core.payment.code.PayResultCode.PAY_APPROVE_SUCCESS;
import static com.woowacamp.soolsool.core.payment.code.PayResultCode.PAY_READY_CANCEL;
import static com.woowacamp.soolsool.core.payment.code.PayResultCode.PAY_READY_FAIL;
import static com.woowacamp.soolsool.core.payment.code.PayResultCode.PAY_READY_SUCCESS;

import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.service.PayService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.common.ApiResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
        @RequestBody final PayOrderRequest payOrderRequest,
        @LoginUser final Long memberId
    ) {
        return ResponseEntity
            .ok(ApiResponse.of(PAY_READY_SUCCESS, payService.payReady(memberId, payOrderRequest)));
    }

    @PostMapping("/approve/{receiptId}")
    public ResponseEntity<ApiResponse<Long>> payApprove(
        @RequestParam("pg_token") final String pgToken,
        @PathVariable("receiptId") final Long receiptId,
        @LoginUser final Long memberId
    ) {
        final Long orderId = payService.payApprove(pgToken, receiptId);
        return ResponseEntity.ok(ApiResponse.of(PAY_APPROVE_SUCCESS, orderId));
    }

    @NoAuth
    @GetMapping("/success/{receiptId}")
    public void kakaoPaySuccess(
        final HttpServletResponse response,
        @RequestParam("pg_token") final String pgToken,
        @PathVariable("receiptId") final Long receiptId
    ) throws IOException {
        final Long orderId = payService.payApprove(pgToken, receiptId);
        response.sendRedirect("http://localhost:3000/order/complete/" + orderId);
    }

    @NoAuth
    @GetMapping("/cancel/{receiptId}")
    public ResponseEntity<ApiResponse<Long>> kakaoPayCancel() {
        return ResponseEntity.ok(ApiResponse.of(PAY_READY_CANCEL, null));
    }


    @NoAuth
    @GetMapping("/fail/{receiptId}")
    public ResponseEntity<ApiResponse<Long>> kakaoPayFail() {
        return ResponseEntity.ok(ApiResponse.of(PAY_READY_FAIL, null));
    }

}
