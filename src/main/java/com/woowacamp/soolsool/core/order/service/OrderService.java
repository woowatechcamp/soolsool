package com.woowacamp.soolsool.core.order.service;

import static com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType.CANCELED;
import static com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType.COMPLETED;

import com.woowacamp.soolsool.core.order.code.OrderErrorCode;
import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.OrderStatus;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import com.woowacamp.soolsool.core.order.dto.response.OrderDetailResponse;
import com.woowacamp.soolsool.core.order.dto.response.OrderListResponse;
import com.woowacamp.soolsool.core.order.repository.OrderRepository;
import com.woowacamp.soolsool.core.order.repository.OrderStatusRepository;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final int PERCENTAGE_BIAS = 100;

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    @Transactional
    public Order addOrder(final Long memberId, final Receipt receipt) {
        final OrderStatus orderStatus = getOrderStatusByType(COMPLETED);

        final Order order = Order.builder()
            .memberId(memberId)
            .orderStatus(orderStatus)
            .receipt(receipt)
            .build();

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse orderDetail(final Long memberId, final Long orderId) {
        final Order order = orderRepository.findOrderById(orderId)
            .orElseThrow(() -> new SoolSoolException(OrderErrorCode.NOT_EXISTS_ORDER));

        validateAccessible(memberId, order);

        return OrderDetailResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderListResponse> orderList(final Long memberId, final Pageable pageable) {
        final Page<Order> orders = orderRepository.findAllByMemberId(memberId, pageable);

        return orders.getContent().stream()
            .map(OrderListResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void modifyOrderStatusCancel(final Long memberId, final Long orderId) {
        final Order order = orderRepository.findOrderById(orderId)
            .orElseThrow(() -> new SoolSoolException(OrderErrorCode.NOT_EXISTS_ORDER));

        validateAccessible(memberId, order);

        final OrderStatus cancelOrderStatus = getOrderStatusByType(CANCELED);

        order.updateStatus(cancelOrderStatus);
    }

    @Transactional(readOnly = true)
    public Double getOrderRatioByLiquorId(final Long liquorId) {
        return orderRepository.findOrderRatioByLiquorId(liquorId)
            .orElse(0.0) * PERCENTAGE_BIAS;
    }

    private void validateAccessible(final Long memberId, final Order order) {
        if (!Objects.equals(memberId, order.getMemberId())) {
            throw new SoolSoolException(OrderErrorCode.ACCESS_DENIED_ORDER);
        }
    }

    private OrderStatus getOrderStatusByType(final OrderStatusType type) {
        return orderStatusRepository.findByType(type)
            .orElseThrow(() -> new SoolSoolException(OrderErrorCode.NOT_FOUND_ORDER_STATUS));
    }
}
