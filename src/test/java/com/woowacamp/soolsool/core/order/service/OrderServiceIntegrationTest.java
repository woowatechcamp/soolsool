package com.woowacamp.soolsool.core.order.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import com.woowacamp.soolsool.core.order.repository.OrderRepository;
import com.woowacamp.soolsool.core.order.repository.OrderStatusRepository;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptPrice;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptQuantity;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptRepository;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptStatusRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(OrderService.class)
@DisplayName("OrderService 통합 테스트")
class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptStatusRepository receiptStatusRepository;

    @Autowired
    private LiquorRegionRepository liquorRegionRepository;

    @Autowired
    private LiquorBrewRepository liquorBrewRepository;

    @Test
    @DisplayName("주문 생성 시 Receipt가 존재하지 않을 경우 SoolSoolException을 던진다.")
    void failSaveOrderWhenNotExistsReceipt() {
        // given

        // when & then
        assertThatThrownBy(() -> orderService.saveOrder(1L, 99999L))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문 내역을 생성할 주문서가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("주문 상세내역이 존재하지 않을 경우 SoolSoolException을 던진다.")
    void failOrderDetailWhenNotExistsOrder() {
        // given

        // when & then
        assertThatThrownBy(() -> orderService.orderDetail(1L, 99999L))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문 내역이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("다른 사용자의 주문 상세내역을 조회할 경우 SoolSoolException을 던진다.")
    void failOrderDetailWhenAccessToOthers() {
        // given
        Long memberId = 1L;
        Long otherMemberId = 2L;

        Receipt othersReceipt = createReceipt(otherMemberId, 1L);
        Order othersOrder = new Order(
            otherMemberId,
            orderStatusRepository.findByType(OrderStatusType.COMPLETED).get(),
            othersReceipt
        );
        Long savedOthersOrderId = orderRepository.save(othersOrder).getId();

        // when & then
        assertThatThrownBy(() -> orderService.orderDetail(memberId, savedOthersOrderId))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("본인의 주문 내역만 조회할 수 있습니다.");
    }

    @Test
    @DisplayName("주문 상태 변경 시 Order가 존재하지 않을 경우 SoolSoolException을 던진다.")
    void failModifyOrderWhenNotExistsOrder() {
        // given

        // when & then
        assertThatThrownBy(() -> orderService.modifyOrderStatusCancel(1L, 99999L))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문 내역이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("다른 사용자의 주문 상세내역을 변경할 경우 SoolSoolException을 던진다.")
    void failModifyOrderWhenAccessToOthers() {
        // given
        Long memberId = 1L;
        Long otherMemberId = 2L;

        Receipt othersReceipt = createReceipt(otherMemberId, 1L);
        Order othersOrder = new Order(
            otherMemberId,
            orderStatusRepository.findByType(OrderStatusType.COMPLETED).get(),
            othersReceipt
        );
        Long savedOthersOrderId = orderRepository.save(othersOrder).getId();

        // when & then
        assertThatThrownBy(() -> orderService.modifyOrderStatusCancel(memberId, savedOthersOrderId))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("본인의 주문 내역만 조회할 수 있습니다.");
    }

    private Receipt createReceipt(final Long memberId, final Long liquorId) {
        Receipt receipt = new Receipt(
            memberId,
            receiptStatusRepository.findByType(ReceiptStatusType.COMPLETED).get(),
            new ReceiptPrice(new BigInteger("10000")),
            new ReceiptPrice(new BigInteger("1000")),
            new ReceiptPrice(new BigInteger("9000")),
            new ReceiptQuantity(10),
            new ArrayList<>(List.of())
        );

        ReceiptItem receiptItem = new ReceiptItem(
            receipt,
            liquorId,
            liquorBrewRepository.findByType(LiquorBrewType.SOJU).get(),
            liquorRegionRepository.findByType(LiquorRegionType.GYEONGSANGNAM_DO).get(),
            "안동 소주", "10000", "10000", "안동", "/soju.jpeg",
            21.7, 400, 1,
            LocalDateTime.now().plusYears(5L)
        );

        receipt.addReceiptItems(List.of(receiptItem));

        return receiptRepository.save(receipt);
    }
}
