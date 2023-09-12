package com.woowacamp.soolsool.core.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacamp.soolsool.config.CacheTestConfig;
import com.woowacamp.soolsool.core.member.domain.OrderMemberServiceImpl;
import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import com.woowacamp.soolsool.core.order.repository.OrderQueryRepository;
import com.woowacamp.soolsool.core.order.repository.OrderStatusCache;
import com.woowacamp.soolsool.global.config.QuerydslConfig;
import com.woowacamp.soolsool.global.config.RedissonConfig;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import(
    {OrderService.class, OrderStatusCache.class, OrderMemberServiceImpl.class,
    OrderQueryRepository.class,
    QuerydslConfig.class,
    RedissonConfig.class, CacheTestConfig.class}
)
@DisplayName("통합 테스트: OrderService")
class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql",
        "/order-type.sql", "/order.sql"
    })
    @DisplayName("주문 상세 내역 조회 시 주문이 존재하지 않을 경우 SoolSoolException을 던진다.")
    void failOrderDetailWhenNotExistsOrder() {
        // given
        Long 김배달 = 1L;

        // when & then
        assertThatThrownBy(() -> orderService.orderDetail(김배달, 99999L))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문 내역이 존재하지 않습니다.");
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql",
        "/order-type.sql", "/order.sql"
    })
    @DisplayName("다른 사용자의 주문 상세내역을 조회할 경우 SoolSoolException을 던진다.")
    void failOrderDetailWhenAccessToOthers() {
        // given
        Long 최민족 = 2L;
        Long 김배달_주문 = 1L;

        // when & then
        assertThatThrownBy(() -> orderService.orderDetail(최민족, 김배달_주문))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("본인의 주문 내역만 조회할 수 있습니다.");
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql",
        "/order-type.sql", "/order.sql"
    })
    @DisplayName("주문 상태 변경 시 Order가 존재하지 않을 경우 SoolSoolException을 던진다.")
    void failModifyOrderWhenNotExistsOrder() {
        // given
        Long 김배달 = 1L;

        // when & then
        assertThatThrownBy(() -> orderService.cancelOrder(김배달, 99999L))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문 내역이 존재하지 않습니다.");
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql",
        "/order-type.sql", "/order.sql"
    })
    @DisplayName("다른 사용자의 주문 상세내역을 변경할 경우 SoolSoolException을 던진다.")
    void failModifyOrderWhenAccessToOthers() {
        // given
        Long 최민족 = 2L;
        Long 김배달_주문 = 1L;

        // when & then
        assertThatThrownBy(() -> orderService.cancelOrder(최민족, 김배달_주문))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("본인의 주문 내역만 조회할 수 있습니다.");
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql",
        "/order-type.sql", "/order.sql"
    })
    @DisplayName("주문을 취소한다.")
    void cancelOrder() throws Exception {
        /* given */
        Long 김배달 = 1L;
        Long 김배달_주문 = 1L;

        /* when */
        Order order = orderService.cancelOrder(김배달, 김배달_주문);

        /* then */
        assertThat(order.getStatus().getType()).isEqualTo(OrderStatusType.CANCELED);
    }
}
