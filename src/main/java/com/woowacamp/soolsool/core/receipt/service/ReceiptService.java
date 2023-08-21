package com.woowacamp.soolsool.core.receipt.service;

import static com.woowacamp.soolsool.core.member.code.MemberErrorCode.MEMBER_NO_INFORMATION;

import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItems;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
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
    public void addReceipt(final Long memberId) {
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
            receiptItems
        );
        receiptRepository.save(receipt);

    }


    private List<ReceiptItem> makeReceiptItems(final List<CartItem> cartItems) {
        return cartItems.stream()
            .map(cartItem -> ReceiptItem.of(cartItem.getLiquor(), cartItem.getQuantity()))
            .collect(Collectors.toList());
    }
}
