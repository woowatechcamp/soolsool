package com.woowacamp.soolsool.core.payment.controller;

import static com.woowacamp.soolsool.core.payment.code.PayResultCode.PAY_READY_CANCEL;
import static com.woowacamp.soolsool.core.payment.code.PayResultCode.PAY_READY_FAIL;
import static com.woowacamp.soolsool.core.payment.code.PayResultCode.PAY_READY_SUCCESS;

import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.dto.response.PaySuccessResponse;
import com.woowacamp.soolsool.core.payment.service.PayService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.common.ApiResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    @PostMapping("/ready")
    public ResponseEntity<ApiResponse<PayReadyResponse>> payReady(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId,
        @RequestBody final PayOrderRequest payOrderRequest
    ) {
        log.info("{} {} | memberId : {} | request : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(),
            memberId, payOrderRequest);

        return ResponseEntity.ok(
            ApiResponse.of(PAY_READY_SUCCESS, payService.ready(memberId, payOrderRequest)));
    }

    @NoAuth
    @GetMapping("/success/{receiptId}")
    public ResponseEntity<ApiResponse<PaySuccessResponse>> kakaoPaySuccess(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId,
        @PathVariable("receiptId") final Long receiptId,
        @RequestParam("pg_token") final String pgToken
    ) {
        log.info("{} {} | memberId : {} | pg_token : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId, pgToken);

        final Order order = payService.approve(memberId, receiptId, pgToken);

        return ResponseEntity.ok(
            ApiResponse.of(PAY_READY_SUCCESS, new PaySuccessResponse(order.getId())));
    }

    @NoAuth
    @GetMapping("/cancel/{receiptId}")
    public ResponseEntity<ApiResponse<Long>> kakaoPayCancel(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId
    ) {
        log.info("{} {} | memberId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId);

        return ResponseEntity.ok(ApiResponse.of(PAY_READY_CANCEL, null));
    }

    @NoAuth
    @GetMapping("/fail/{receiptId}")
    public ResponseEntity<ApiResponse<Long>> kakaoPayFail(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId
    ) {
        log.info("{} {} | memberId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId);

        return ResponseEntity.ok(ApiResponse.of(PAY_READY_FAIL, null));
    }

}
