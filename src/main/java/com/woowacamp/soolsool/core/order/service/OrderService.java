package com.woowacamp.soolsool.core.order.service;

import static com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType.CANCELED;
import static com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType.COMPLETED;

import com.woowacamp.soolsool.core.order.code.OrderErrorCode;
import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.OrderPaymentInfo;
import com.woowacamp.soolsool.core.order.domain.OrderStatus;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import com.woowacamp.soolsool.core.order.dto.response.OrderDetailResponse;
import com.woowacamp.soolsool.core.order.dto.response.OrderListResponse;
import com.woowacamp.soolsool.core.order.dto.response.PageOrderListResponse;
import com.woowacamp.soolsool.core.order.repository.OrderPaymentInfoRepository;
import com.woowacamp.soolsool.core.order.repository.OrderQueryRepository;
import com.woowacamp.soolsool.core.order.repository.OrderRepository;
import com.woowacamp.soolsool.core.order.repository.OrderStatusCache;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import com.woowacamp.soolsool.global.infra.LockType;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final long LOCK_WAIT_TIME = 3L;
    private static final long LOCK_LEASE_TIME = 3L;
    private static final int PERCENTAGE_BIAS = 100;

    private final OrderRepository orderRepository;
    private final OrderPaymentInfoRepository orderPaymentInfoRepository;
    private final OrderStatusCache orderStatusCache;
    private final OrderMemberService orderMemberService;
    private final OrderQueryRepository orderQueryRepository;

    private final RedissonClient redissonClient;

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

        final OrderPaymentInfo orderPaymentInfo = orderPaymentInfoRepository
            .findPaymentInfoByOrderId(orderId)
            .orElseThrow(() -> new SoolSoolException(OrderErrorCode.NOT_EXISTS_PAYMENT_INFO));

        return OrderDetailResponse.of(order, orderPaymentInfo);
    }

    @Transactional(readOnly = true)
    public PageOrderListResponse orderList(
        final Long memberId,
        final Pageable pageable,
        final Long cursorId
    ) {
        final List<OrderListResponse> orders = orderQueryRepository
            .findAllByMemberId(memberId, pageable, cursorId);

        if (orders.size() < pageable.getPageSize()) {
            return PageOrderListResponse.of(false, orders);
        }

        final Long lastReadOrderId = orders.get(orders.size() - 1).getOrderId();

        return PageOrderListResponse.of(true, lastReadOrderId, orders);
    }

    @Transactional
    public Order cancelOrder(final Long memberId, final Long orderId) {
        final RLock memberLock = redissonClient.getLock(LockType.MEMBER.getPrefix() + memberId);
        final RLock orderLock = redissonClient.getLock(LockType.ORDER.getPrefix() + orderId);

        try {
            memberLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
            orderLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            final Order order = orderRepository.findOrderById(orderId)
                .orElseThrow(() -> new SoolSoolException(OrderErrorCode.NOT_EXISTS_ORDER));

            validateAccessible(memberId, order);

            final OrderStatus cancelOrderStatus = orderStatusCache.findByType(CANCELED)
                .orElseThrow(() -> new SoolSoolException(OrderErrorCode.NOT_EXISTS_ORDER_STATUS));

            order.updateStatus(cancelOrderStatus);
            orderMemberService.refundMileage(memberId, order.getMileageUsage());

            return order;
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new SoolSoolException(OrderErrorCode.INTERRUPTED_THREAD);
        } finally {
            unlock(orderLock);
            unlock(memberLock);
        }
    }

    private void unlock(final RLock rLock) {
        if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
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
        return orderStatusCache.findByType(type)
            .orElseThrow(() -> new SoolSoolException(OrderErrorCode.NOT_EXISTS_ORDER_STATUS));
    }

    @Transactional
    public Long addPaymentInfo(final OrderPaymentInfo orderPaymentInfo) {
        return orderPaymentInfoRepository.save(orderPaymentInfo).getId();
    }
}
