package com.woowacamp.soolsool.core.payment.service;

import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.core.liquor.service.LiquorStockService;
import com.woowacamp.soolsool.core.member.service.MemberService;
import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.service.OrderService;
import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.infra.PayClient;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayService {

    private final ReceiptService receiptService;
    private final MemberService memberService;
    private final OrderService orderService;
    private final CartService cartService;
    private final LiquorStockService liquorStockService;
    private final LiquorService liquorService;

    private final PayClient payClient;

    @Transactional
    public PayReadyResponse ready(final Long memberId, final PayOrderRequest payOrderRequest) {
        final Receipt receipt = receiptService
            .getMemberReceipt(memberId, payOrderRequest.getReceiptId());

        return payClient.ready(receipt);
    }

    @Transactional
    public Order approve(final Long memberId, final Long receiptId, final String pgToken) {
        final Receipt receipt = receiptService.getMemberReceipt(memberId, receiptId);

        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            liquorStockService.decreaseLiquorStock(receiptItem.getLiquorId(),
                receiptItem.getQuantity());
            liquorService.decreaseTotalStock(receiptItem.getLiquorId(), receiptItem.getQuantity());
        }

        final Order order = orderService.addOrder(memberId, receipt);

        memberService
            .subtractMemberMileage(memberId, order, receipt.getMileageUsage());

        cartService.removeCartItems(memberId);

        orderService.addPaymentInfo(payClient.payApprove(receipt, pgToken).toEntity(orderId));

        return order;
    }
}
