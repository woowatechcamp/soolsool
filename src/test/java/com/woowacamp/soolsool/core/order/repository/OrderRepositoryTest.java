package com.woowacamp.soolsool.core.order.repository;

import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.OrderStatus;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql",
        "/order-type.sql", "/order.sql"
})
@DisplayName("단위 테스트: OrderRepository")
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Test
    @DisplayName("주문율을 구한다.")
    void findOrderRatioByLiquorId() {
        /* given */


        /* when */
        Optional<Double> ratio = orderRepository.findOrderRatioByLiquorId(1L);

        /* then */
        assertThat(ratio).isPresent().contains(0.5);
    }

    @Test
    @DisplayName("시작일, 종료일, 주문 상태에 따른 주문 목록을 구한다.")
    void findAllOrdersByCreatedAtAndOrserStatus() {
        /* given */
        LocalDateTime startDate = LocalDateTime.of(2023, 8, 1, 0, 0);
        LocalDateTime now = LocalDateTime.now();
        OrderStatus orderStatus = orderStatusRepository.findById(1L).get();

        /* when */
        List<Order> orders = orderRepository
                .findAllByCreatedAtBetweenAndStatus(startDate, now, orderStatus);

        /* then */
        assertThat(orders).size().isEqualTo(1);
        System.out.println(orders);
    }

    @Test
    @DisplayName("시작일, 종료일, 주문 상태에 따른 주문 목록을 구한다.")
    void findAllOrdersByNotCreatedAtAndOrderStatus() {
        /* given */
        LocalDateTime startDate = LocalDateTime.of(2023, 8, 1, 0, 0);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowBefore = now.minusDays(1L);
        OrderStatus orderStatus = orderStatusRepository.findById(1L).get();

        /* when */
        List<Order> orders = orderRepository
                .findAllByCreatedAtBetweenAndStatus(startDate, nowBefore, orderStatus);

        /* then */
        assertThat(orders).size().isEqualTo(0);
        System.out.println(orders);
    }

    @Test
    @DisplayName("시작일, 종료일, 주문 상태에 따른 주문 목록을 구한다.")
    void findAllOrdersByCreatedAtAndNotOrderStatus() {
        /* given */
        LocalDateTime startDate = LocalDateTime.of(2023, 8, 1, 0, 0);
        LocalDateTime now = LocalDateTime.now();
        OrderStatus orderStatus = orderStatusRepository.findById(2L).get();

        /* when */
        List<Order> orders = orderRepository
                .findAllByCreatedAtBetweenAndStatus(startDate, now, orderStatus);

        /* then */
        assertThat(orders).size().isEqualTo(0);
        System.out.println(orders);
    }
}
