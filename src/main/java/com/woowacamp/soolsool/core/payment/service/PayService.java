package com.woowacamp.soolsool.core.payment.service;

import static com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType.COMPLETED;
import static com.woowacamp.soolsool.core.payment.code.PayErrorCode.ACCESS_DENIED_RECEIPT;
import static com.woowacamp.soolsool.core.payment.code.PayErrorCode.NOT_FOUND_LIQUOR;
import static com.woowacamp.soolsool.core.payment.code.PayErrorCode.NOT_FOUND_ORDER_STATUS;
import static com.woowacamp.soolsool.core.payment.code.PayErrorCode.NOT_FOUND_RECEIPT;
import static com.woowacamp.soolsool.core.payment.code.PayErrorCode.NOT_MATCHED_LIQUOR_PRICE;

import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.OrderStatus;
import com.woowacamp.soolsool.core.order.repository.OrderRepository;
import com.woowacamp.soolsool.core.order.repository.OrderStatusRepository;
import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import com.woowacamp.soolsool.global.infra.SimplePayService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayService {

    private final SimplePayService kakaoPay;
    private final ReceiptRepository receiptRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final CartItemRepository cartItemRepository;
    private final LiquorRepository liquorRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public PayReadyResponse payReady(final Long memberId, final PayOrderRequest payOrderRequest) {

        final Receipt receipt = receiptRepository.findById(payOrderRequest.getReceiptId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영수증입니다."));

        validateAccessible(memberId, receipt);
        final PayReadyResponse kakaoPayReadyResponse = kakaoPay.payReady(receipt);

        //System.out.println(kakaoPayReadyResponse);

        return kakaoPayReadyResponse;
    }

    @Transactional
    public Long payApprove(final Long memberId, final String pgToken, final Long receiptId) {
        final Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new SoolSoolException(NOT_FOUND_RECEIPT));
        final List<ReceiptItem> receiptItems = receipt.getReceiptItems();

        receiptItems.forEach(receiptItem -> {
            final Liquor liquor = liquorRepository.findLiquorByIdWithLock(receiptItem.getLiquorId())
                .orElseThrow(() -> new SoolSoolException(NOT_FOUND_LIQUOR));
            if (!Objects.equals(liquor.getPrice(), receiptItem.getLiquorOriginalPrice())) {
                throw new SoolSoolException(NOT_MATCHED_LIQUOR_PRICE);
            }
            liquor.decreaseStock(receiptItem.getQuantity());
        });

        validateAccessible(memberId, receipt);

        final Member member = memberRepository.findByIdWithLock(memberId)
            .orElseThrow(() -> new SoolSoolException(NOT_FOUND_RECEIPT));
        member.decreasePoint(receipt.getMileageUsage());

        // TODO : 나중에 결제 정보 저장
        kakaoPay.payApprove(receipt, pgToken);

        final OrderStatus orderStatus = orderStatusRepository.findByType(COMPLETED)
            .orElseThrow(() -> new SoolSoolException(NOT_FOUND_ORDER_STATUS));
        final Order order = Order.of(memberId, orderStatus, receipt);

        cartItemRepository.deleteAllByMemberId(memberId);

        return orderRepository.save(order).getId();
    }

    private void validateAccessible(final Long memberId, final Receipt receipt) {
        if (!Objects.equals(memberId, receipt.getMemberId())) {
            throw new SoolSoolException(ACCESS_DENIED_RECEIPT);
        }
    }
}
