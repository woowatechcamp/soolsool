package com.woowacamp.soolsool.core.order.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import(OrderService.class)
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
        "/order-type.sql"
    })
    @DisplayName("주문 생성 시 Receipt가 존재하지 않을 경우 SoolSoolException을 던진다.")
    void failSaveOrderWhenNotExistsReceipt() {
        // given
        Long 김배달 = 1L;

        // when & then
        assertThatThrownBy(() -> orderService.saveOrder(김배달, 99999L))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문 내역을 생성할 주문서가 존재하지 않습니다.");
    }

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
        assertThatThrownBy(() -> orderService.modifyOrderStatusCancel(김배달, 99999L))
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
        assertThatThrownBy(() -> orderService.modifyOrderStatusCancel(최민족, 김배달_주문))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("본인의 주문 내역만 조회할 수 있습니다.");
    }
}
