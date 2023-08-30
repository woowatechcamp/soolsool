package com.woowacamp.soolsool.core.order.controller;

import com.woowacamp.soolsool.core.order.code.OrderResultCode;
import com.woowacamp.soolsool.core.order.dto.response.OrderDetailResponse;
import com.woowacamp.soolsool.core.order.dto.response.OrderRatioResponse;
import com.woowacamp.soolsool.core.order.dto.response.PageOrderListResponse;
import com.woowacamp.soolsool.core.order.service.OrderService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.common.ApiResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> orderDetail(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId,
        @PathVariable final Long orderId
    ) {
        log.info("{} {} | memberId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId);

        final OrderDetailResponse response = orderService.orderDetail(memberId, orderId);

        return ResponseEntity.ok(ApiResponse.of(OrderResultCode.ORDER_DETAIL_SUCCESS, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageOrderListResponse>> orderList(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId,
        @PageableDefault final Pageable pageable,
        @RequestParam(required = false) final Long cursorId
    ) {
        log.info("{} {} | memberId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId);

        final PageOrderListResponse response = orderService.orderList(memberId, pageable, cursorId);
        
        return ResponseEntity.ok(ApiResponse.of(OrderResultCode.ORDER_DETAIL_SUCCESS, response));
    }

    @NoAuth
    @GetMapping("/ratio")
    public ResponseEntity<ApiResponse<OrderRatioResponse>> getOrderRatioByLiquorId(
        final HttpServletRequest httpServletRequest,
        @RequestParam final Long liquorId
    ) {
        log.info("{} {} | liquorId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), liquorId);

        final Double ratio = orderService.getOrderRatioByLiquorId(liquorId);

        return ResponseEntity.ok(ApiResponse
            .of(OrderResultCode.ORDER_RATIO_SUCCESS, new OrderRatioResponse(ratio)));
    }

    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId,
        @PathVariable final Long orderId
    ) {
        log.info("{} {} | memberId : {} | orderId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId, orderId);

        orderService.cancelOrder(memberId, orderId);

        return ResponseEntity.ok(ApiResponse.from(OrderResultCode.ORDER_CANCEL_SUCCESS));
    }
}
