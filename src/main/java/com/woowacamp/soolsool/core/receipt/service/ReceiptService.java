package com.woowacamp.soolsool.core.receipt.service;

import static com.woowacamp.soolsool.core.member.code.MemberErrorCode.MEMBER_NO_INFORMATION;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_EQUALS_MEMBER;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_RECEIPT_FOUND;
import static com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType.INPROGRESS;

import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItems;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptStatus;
import com.woowacamp.soolsool.core.receipt.dto.ReceiptModifyRequest;
import com.woowacamp.soolsool.core.receipt.dto.ReceiptResponse;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addReceipt(final Long memberId) {
        final Member member = memberRepository
            .findById(memberId)
            .orElseThrow(() -> new SoolSoolException(MEMBER_NO_INFORMATION));

        final List<CartItem> cartItems = cartItemRepository
            .findAllByMemberIdOrderByCreatedAtDescWithLiquor(memberId);

        final ReceiptItems receiptItems = new ReceiptItems(
            makeReceiptItems(cartItems),
            member.getMileage()
        );

        final Receipt receipt = Receipt.of(
            memberId,
            new ReceiptStatus(INPROGRESS),
            receiptItems
        );

        return receiptRepository.save(receipt).getId();
    }


    private List<ReceiptItem> makeReceiptItems(final List<CartItem> cartItems) {
        return cartItems.stream()
            .map(cartItem -> ReceiptItem.of(cartItem.getLiquor(), cartItem.getQuantity()))
            .collect(Collectors.toList());
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
    public void modifyReceiptStatus(final Long memberId, final Long receiptId,
        final ReceiptModifyRequest requestModifyRequest
    ) {
        final Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new SoolSoolException(NOT_RECEIPT_FOUND));

        if (!Objects.equals(receipt.getMemberId(), memberId)) {
            throw new SoolSoolException(NOT_EQUALS_MEMBER);
        }
        receipt.updateStatus(requestModifyRequest.getReceiptStatusType());
    }
}
