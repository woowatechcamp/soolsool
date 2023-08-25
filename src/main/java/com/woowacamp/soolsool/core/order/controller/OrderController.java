package com.woowacamp.soolsool.core.order.controller;

import com.woowacamp.soolsool.core.order.code.OrderResultCode;
import com.woowacamp.soolsool.core.order.dto.response.OrderDetailResponse;
import com.woowacamp.soolsool.core.order.dto.response.OrderListResponse;
import com.woowacamp.soolsool.core.order.service.OrderService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.common.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final String DEFAULT_URL = "/orders";

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> orderDetail(
        @LoginUser final Long memberId,
        @PathVariable final Long orderId
    ) {
        log.info("GET {}/{} | memberId : {}",
            DEFAULT_URL, orderId, memberId);

        final OrderDetailResponse response = orderService.orderDetail(memberId, orderId);

        return ResponseEntity.ok(ApiResponse.of(OrderResultCode.ORDER_DETAIL_SUCCESS, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderListResponse>>> orderList(
        @LoginUser final Long memberId,
        @PageableDefault final Pageable pageable
    ) {
        log.info("GET {} | memberId : {}",
            DEFAULT_URL, memberId);

        final List<OrderListResponse> response = orderService.orderList(memberId, pageable);

        return ResponseEntity.ok(ApiResponse.of(OrderResultCode.ORDER_DETAIL_SUCCESS, response));
    }
}
