package com.woowacamp.soolsool.core.receipt.service;

import static com.woowacamp.soolsool.core.member.code.MemberErrorCode.MEMBER_NO_INFORMATION;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_EQUALS_MEMBER;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_RECEIPT_FOUND;

import com.woowacamp.soolsool.core.cart.domain.Cart;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import com.woowacamp.soolsool.core.receipt.dto.request.ReceiptModifyRequest;
import com.woowacamp.soolsool.core.receipt.dto.response.ReceiptResponse;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final ReceiptMapper receiptMapper;
    private final ReceiptRepository receiptRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    
    @Transactional
    public Long addReceipt(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new SoolSoolException(MEMBER_NO_INFORMATION));

        final Cart cart =
            new Cart(memberId, cartItemRepository.findAllByMemberId(memberId));

        return receiptRepository.save(receiptMapper.mapFrom(cart, member.getMileage())).getId();
    }

    @Transactional(readOnly = true)
    public ReceiptResponse findReceipt(final Long memberId, final Long receiptId) {
        final Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new SoolSoolException(NOT_RECEIPT_FOUND));

        if (!Objects.equals(receipt.getMemberId(), memberId)) {
            throw new SoolSoolException(NOT_EQUALS_MEMBER);
        }

        return ReceiptResponse.from(receipt);
    }

    @Transactional
    public void modifyReceiptStatus(
        final Long memberId,
        final Long receiptId,
        final ReceiptModifyRequest requestModifyRequest
    ) {
        final Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new SoolSoolException(NOT_RECEIPT_FOUND));

        if (!Objects.equals(receipt.getMemberId(), memberId)) {
            throw new SoolSoolException(NOT_EQUALS_MEMBER);
        }

        final ReceiptStatusType type =
            ReceiptStatusType.valueOf(requestModifyRequest.getReceiptStatusType());

        receipt.updateStatus(type);
    }

}
