package com.woowacamp.soolsool.core.order.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.core.order.domain.OrderPaymentInfo;
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
@DisplayName("단위 테스트: OrderPaymentInfoRepository")
class OrderPaymentInfoRepositoryTest {

    @Autowired
    OrderPaymentInfoRepository orderPaymentInfoRepository;

    @Test
    @DisplayName("결제 정보를 조회한다.")
    void findOrderRatioByLiquorId() {
        /* given */


        /* when */
        OrderPaymentInfo orderPaymentInfo = orderPaymentInfoRepository.findPaymentInfoByOrderId(1L)
            .orElseThrow(() -> new RuntimeException("결제 수단이 존재하지 않습니다."));

        assertAll(
            () -> assertThat(orderPaymentInfo.getOrderId()).isEqualTo(1L),
            () -> assertThat(orderPaymentInfo.getApprovedId()).isEqualTo("43623044"),
            () -> assertThat(orderPaymentInfo.getBin()).isEqualTo("56369819"),
            () -> assertThat(orderPaymentInfo.getCardMid()).isEqualTo("379113825"),
            () -> assertThat(orderPaymentInfo.getInstallMonth()).isEqualTo("6"),
            () -> assertThat(orderPaymentInfo.getPaymentMethodType()).isEqualTo("CARD"),
            () -> assertThat(orderPaymentInfo.getPurchaseCorp()).isEqualTo("우리카드")
        );
    }
}
