package com.woowacamp.soolsool.core.payment.service;

import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.core.liquor.service.LiquorStockService;
import com.woowacamp.soolsool.core.member.domain.vo.MemberMileage;
import com.woowacamp.soolsool.core.member.service.MemberService;
import com.woowacamp.soolsool.core.order.service.OrderService;
import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.infra.PayClient;
import com.woowacamp.soolsool.core.payment.repository.PayRepository;
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

    private final PayRepository payRepository;

    @Transactional
    public PayReadyResponse payReady(final Long memberId, final PayOrderRequest payOrderRequest) {
        final Receipt receipt = receiptService
            .getMemberReceipt(memberId, payOrderRequest.getReceiptId());

        return payClient.ready(receipt);
    }

    @Transactional
    public Long approve(final Long memberId, final Long receiptId, final String pgToken) {
        final Receipt receipt = receiptService.getMemberReceipt(memberId, receiptId);

        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            liquorStockService.decreaseLiquorStock(receiptItem.getLiquorId(),
                receiptItem.getQuantity());
            liquorService.decreaseTotalStock(receiptItem.getLiquorId(), receiptItem.getQuantity());
        }

        memberService.subtractMemberMileage(memberId, new MemberMileage(receipt.getMileageUsage()));

        final Long orderId = orderService.addOrder(memberId, receipt);

        cartService.removeCartItems(memberId);

        payRepository.save(payClient.payApprove(receipt, pgToken).toPayment());

        return orderId;
    }
}
