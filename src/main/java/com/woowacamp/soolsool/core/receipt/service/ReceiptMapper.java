package com.woowacamp.soolsool.core.receipt.service;

import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_FOUND_CART_ITEM;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_RECEIPT_TYPE_FOUND;
import static com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType.INPROGRESS;

import com.woowacamp.soolsool.core.cart.domain.Cart;
import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptStatus;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemPrice;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemQuantity;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptStatusCache;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiptMapper {

    private static final BigInteger MILEAGE_USAGE_PERCENT = BigInteger.valueOf(10L);

    private final ReceiptStatusCache receiptStatusRepository;

    public Receipt mapFrom(final Cart cart, final BigInteger mileage) {
        if (cart.isEmpty()) {
            throw new SoolSoolException(NOT_FOUND_CART_ITEM);
        }

        final ReceiptItemPrice totalPrice = computeTotalPrice(cart);
        final ReceiptItemPrice mileageUsage = computeMileageUsage(mileage);

        return Receipt.builder()
            .memberId(cart.getMemberId())
            .receiptStatus(getReceiptStatusByType(INPROGRESS))
            .originalTotalPrice(totalPrice)
            .mileageUsage(mileageUsage)
            .purchasedTotalPrice(totalPrice.subtract(mileageUsage))
            .totalQuantity(new ReceiptItemQuantity(cart.getCartItemSize()))
            .receiptItems(mapToReceiptItems(cart))
            .build();
    }

    private ReceiptItemPrice computeMileageUsage(final BigInteger mileage) {
        return new ReceiptItemPrice(mileage.divide(MILEAGE_USAGE_PERCENT));
    }

    private List<ReceiptItem> mapToReceiptItems(final Cart cart) {
        return cart.getCartItems().stream()
            .map(cartItem -> ReceiptItem.of(cartItem.getLiquor(), cartItem.getQuantity()))
            .collect(Collectors.toList());
    }

    private ReceiptItemPrice computeTotalPrice(final Cart cart) {
        BigInteger totalPrice = BigInteger.ZERO;

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice = totalPrice.add(cartItem.getLiquorPrice());
        }

        return new ReceiptItemPrice(totalPrice);
    }

    private ReceiptStatus getReceiptStatusByType(final ReceiptStatusType type) {
        return receiptStatusRepository.findByType(type)
            .orElseThrow(() -> new SoolSoolException(NOT_RECEIPT_TYPE_FOUND));
    }
}
