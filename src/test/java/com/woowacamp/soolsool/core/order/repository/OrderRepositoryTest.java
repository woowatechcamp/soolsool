package com.woowacamp.soolsool.core.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

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

    @Test
    @DisplayName("주문율을 구한다.")
    void findOrderRatioByLiquorId() {
        /* given */


        /* when */
        Optional<Double> ratio = orderRepository.findOrderRatioByLiquorId(1L);

        /* then */
        assertThat(ratio).isPresent().contains(0.5);
    }
}
