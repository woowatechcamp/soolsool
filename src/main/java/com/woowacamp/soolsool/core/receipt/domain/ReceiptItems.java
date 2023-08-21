package com.woowacamp.soolsool.core.receipt.domain;

import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_FOUND_CART_ITEM;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

public class ReceiptItems {

    private static final int MILEAGE_PERCENT = 10;
    private final BigInteger totalAmount;
    private final BigInteger mileageUsage;
    private final List<ReceiptItem> items;

    public ReceiptItems(
        final List<ReceiptItem> receiptItems,
        final BigInteger memberMileage
    ) {

        if (receiptItems.isEmpty()) {
            throw new SoolSoolException(NOT_FOUND_CART_ITEM);
        }

        this.items = receiptItems;
        this.totalAmount = findTotalAmount();
        this.mileageUsage = findUsedMileage(memberMileage);
    }

    private BigInteger findTotalAmount() {
        return items.stream()
            .map(ReceiptItem::getTotalAmount)
            .reduce(BigInteger.ZERO, BigInteger::add);
    }

    public BigInteger getTotalAmount() {
        return totalAmount;
    }

    private BigInteger findUsedMileage(final BigInteger memberMileage) {
        final BigInteger canUsedMileage = totalAmount.divide(BigInteger.valueOf(MILEAGE_PERCENT));
        return canUsedMileage.min(memberMileage);
    }

    public int getReceiptItemsSize() {
        return items.size();
    }

    public BigInteger getPurchasedTotalPrice() {
        return totalAmount.subtract(mileageUsage);
    }

    public BigInteger getMileageUsage() {
        return mileageUsage;
    }

    public List<ReceiptItem> getReceiptItems() {
        return Collections.unmodifiableList(items);
    }
}
