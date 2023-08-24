package com.woowacamp.soolsool.core.payment.service;

import static com.woowacamp.soolsool.core.payment.code.PayErrorCode.NOT_FOUND_LIQUOR;
import static com.woowacamp.soolsool.core.payment.code.PayErrorCode.NOT_MATCHED_LIQUOR_PRICE;

import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.service.LiquorStockService;
import com.woowacamp.soolsool.core.member.domain.vo.MemberMileage;
import com.woowacamp.soolsool.core.member.service.MemberService;
import com.woowacamp.soolsool.core.order.service.OrderService;
import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayApproveResponse;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.infra.PayClient;
import com.woowacamp.soolsool.core.payment.repository.PayRepository;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoPayService {

    private final LiquorRepository liquorRepository;

    /* -------- */
    private final ReceiptService receiptService;
    private final MemberService memberService;
    private final OrderService orderService;
    private final CartService cartService;
    private final LiquorStockService liquorStockService;

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
        /* 주문서 찾기 */
        final Receipt receipt = receiptService.getMemberReceipt(memberId, receiptId);

        /* 재고 처리 */
        final List<ReceiptItem> receiptItems = receipt.getReceiptItems();
        for (ReceiptItem receiptItem : receiptItems) {
            final Long liquorId = receiptItem.getLiquorId();

            liquorStockService.decreaseLiquorStock(liquorId, receiptItem.getQuantity());
        }

        // ----

        receipt.getReceiptItems().forEach(receiptItem -> {
            final Liquor liquor = liquorRepository.findLiquorByIdWithLock(receiptItem.getLiquorId())
                .orElseThrow(() -> new SoolSoolException(NOT_FOUND_LIQUOR));
            if (!Objects.equals(liquor.getPrice(), receiptItem.getLiquorOriginalPrice())) {
                throw new SoolSoolException(NOT_MATCHED_LIQUOR_PRICE);
            }
            // TODO: 재고 관련 설계를 마친 후 리팩토링 필요
//            liquor.decreaseStock(receiptItem.getQuantity());
        });

        /* 마일리지 차감 */
        memberService.subtractMemberMileage(memberId, new MemberMileage(receipt.getMileageUsage()));

        /* 주문 내역 등록 */
        final Long orderId = orderService.addOrder(memberId, receipt);

        /* 장바구니 비우기 */
        cartService.removeCartItems(memberId);

        /* 결제 승인 요청 */
        final PayApproveResponse response = payClient.payApprove(receipt, pgToken);
        payRepository.save(response.toPayment());

        return orderId;
    }
}
