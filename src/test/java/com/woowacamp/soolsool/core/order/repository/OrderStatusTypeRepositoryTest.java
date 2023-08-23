package com.woowacamp.soolsool.core.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.order.code.OrderErrorCode;
import com.woowacamp.soolsool.core.order.domain.OrderStatus;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/order-type.sql")
@DisplayName("주문 상태 repository 통합 테스트")
class OrderStatusTypeRepositoryTest {

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Test
    @DisplayName("OrderStatusType으로 OrderStatus를 조회한다.")
    void findByLiquorRegionType_type() {
        // given
        OrderStatus 결제완료 = orderStatusRepository.findByType(OrderStatusType.COMPLETED)
            .orElseThrow(() -> new SoolSoolException(OrderErrorCode.NOT_LIQUOR_STATUS_FOUND));

        // when & then
        assertThat(결제완료.getType().getStatus()).isEqualTo(OrderStatusType.COMPLETED.getStatus());
    }
}
