package com.woowacamp.soolsool.core.payment.service;

import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.OrderStatus;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import com.woowacamp.soolsool.core.order.repository.OrderRepository;
import com.woowacamp.soolsool.core.order.repository.OrderStatusRepository;
import com.woowacamp.soolsool.core.payment.code.PayErrorCode;
import com.woowacamp.soolsool.core.payment.domain.KakaoPayReceipt;
import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.KakaoPayApproveResponse;
import com.woowacamp.soolsool.core.payment.dto.response.KakaoPayReadyResponse;
import com.woowacamp.soolsool.core.payment.infra.KakaoPayImpl;
import com.woowacamp.soolsool.core.payment.repository.KakaoPayReceiptRepository;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayService {

    private final KakaoPayImpl kakaoPay;
    private final ReceiptRepository receiptRepository;
    private final KakaoPayReceiptRepository kakaoPayReceiptRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    @Transactional
    public String payReady(final Long memberId, final PayOrderRequest payOrderRequest) {

        final Receipt receipt = receiptRepository.findById(payOrderRequest.getReceiptId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영수증입니다."));

        validateAccessible(memberId, receipt);
        final KakaoPayReadyResponse kakaoPayReadyResponse = kakaoPay.payReady(receipt);

        final String tid = kakaoPayReadyResponse.getTid();
        kakaoPayReceiptRepository.save(KakaoPayReceipt.of(receipt.getId(), tid));
        //System.out.println(kakaoPayReadyResponse);

        return kakaoPayReadyResponse.getNext_redirect_pc_url();
    }

    public Long payApprove(final Long memberId, final String pgToken, final Long receiptId) {
        final KakaoPayReceipt kakaoPayReceipt = kakaoPayReceiptRepository.findByReceiptId(receiptId)
            .orElseThrow(() -> new SoolSoolException(PayErrorCode.NOT_FOUND_KAKAO_PAY_RECEIPT));

        final Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new SoolSoolException(PayErrorCode.NOT_FOUND_RECEIPT));

        validateAccessible(memberId, receipt);

        final KakaoPayApproveResponse kakaoPayApproveResponse = kakaoPay
            .payApprove(kakaoPayReceipt.getTid(), pgToken);

        final OrderStatus orderStatus = orderStatusRepository.findByType(OrderStatusType.COMPLETED)
            .orElseThrow(() -> new SoolSoolException(PayErrorCode.NOT_FOUND_ORDER_STATUS));

        final Order order = Order.of(memberId, orderStatus, receipt);

        return orderRepository.save(order).getId();
    }

    private void validateAccessible(final Long memberId, final Receipt receipt) {
        if (!Objects.equals(memberId, receipt.getMemberId())) {
            throw new SoolSoolException(PayErrorCode.ACCESS_DENIED_RECEIPT);
        }
    }
}
