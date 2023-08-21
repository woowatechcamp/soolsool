package com.woowacamp.soolsool.core.receipt.service;

import static com.woowacamp.soolsool.core.member.code.MemberErrorCode.MEMBER_NO_INFORMATION;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_FOUND_CART_ITEM;

import com.woowacamp.soolsool.core.cart.domain.Cart;
import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    private static final int MILEAGE_PERCENT = 10;
    private final ReceiptRepository receiptRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addReceipt(Long memberId) {
        final Member member = memberRepository
            .findById(memberId)
            .orElseThrow(() -> new SoolSoolException(MEMBER_NO_INFORMATION));

        final List<CartItem> cartItems = cartItemRepository
            .findAllByMemberIdOrderByCreatedAtDescWithLiquor(memberId);
        if (cartItems.isEmpty()) {
            throw new SoolSoolException(NOT_FOUND_CART_ITEM);
        }
        final Cart cart = new Cart(memberId, cartItems);

        final BigInteger originalTotalPrice = cart.getTotalAmount();
        final BigInteger canUsedMileage = originalTotalPrice
            .divide(BigInteger.valueOf(MILEAGE_PERCENT));
        final BigInteger mileageUsage = canUsedMileage.min(member.getMileage());

        final BigInteger purchasedTotalPrice = findPurchasedTotalPrice(
            originalTotalPrice, mileageUsage);

        final Receipt receipt = Receipt.of(
            memberId,
            purchasedTotalPrice.toString(),
            originalTotalPrice.toString(),
            mileageUsage.toString(),
            cartItems.size()
        );

        receiptRepository.save(receipt);
        receipt.addReceiptItems(makeReceiptItems(cartItems));
    }
    

    private BigInteger findPurchasedTotalPrice(
        final BigInteger originalTotalPrice,
        final BigInteger mileageUsage
    ) {
        return originalTotalPrice.subtract(mileageUsage);
    }

    private List<ReceiptItem> makeReceiptItems(final List<CartItem> cartItems) {
        return cartItems.stream()
            .map(cartItem -> ReceiptItem.of(cartItem.getLiquor(), cartItem.getQuantity()))
            .collect(Collectors.toList());
    }
}
