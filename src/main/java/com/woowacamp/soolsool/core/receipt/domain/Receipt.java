package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.core.receipt.converter.ReceiptPriceConverter;
import com.woowacamp.soolsool.core.receipt.converter.ReceiptQuantityConverter;
import com.woowacamp.soolsool.core.receipt.vo.ReceiptPrice;
import com.woowacamp.soolsool.core.receipt.vo.ReceiptQuantity;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "receipts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receipt extends ReceiptBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    @Getter
    private Long memberId;

    @ColumnDefault("1")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_status_id")
    @Getter
    private ReceiptStatus receiptStatus;

    @Column(name = "original_total_price")
    @Convert(converter = ReceiptPriceConverter.class)
    private ReceiptPrice originalTotalPrice;

    @Column(name = "mileage_usage")
    @Convert(converter = ReceiptPriceConverter.class)
    private ReceiptPrice mileageUsage;

    @Column(name = "purchased_total_price")
    @Convert(converter = ReceiptPriceConverter.class)
    private ReceiptPrice purchasedTotalPrice;

    @Column(name = "total_quantity")
    @Convert(converter = ReceiptQuantityConverter.class)
    private ReceiptQuantity totalQuantity;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<ReceiptItem> receiptItems = new ArrayList<>();

    public Receipt(
        final Long id,
        final Long memberId,
        final String originalTotalPrice,
        final String mileageUsage,
        final String purchasedTotalPrice,
        final int totalQuantity,
        final List<ReceiptItem> receiptItems
    ) {
        this.id = id;
        this.memberId = memberId;
        this.originalTotalPrice = new ReceiptPrice(new BigInteger(originalTotalPrice));
        this.mileageUsage = new ReceiptPrice(new BigInteger(mileageUsage));
        this.purchasedTotalPrice = new ReceiptPrice(new BigInteger(purchasedTotalPrice));
        this.totalQuantity = new ReceiptQuantity(totalQuantity);
        addReceiptItems(receiptItems);
    }

    public static Receipt of(
        final Long memberId,
        final ReceiptItems receiptItems
    ) {
        return new Receipt(
            null, memberId,
            receiptItems.getTotalAmount().toString(),
            receiptItems.getMileageUsage().toString(),
            receiptItems.getPurchasedTotalPrice().toString(),
            receiptItems.getReceiptItemsSize(),
            receiptItems.getReceiptItems()
        );
    }

    public void addReceiptItems(final List<ReceiptItem> receiptItems) {
        this.receiptItems.addAll(receiptItems);
        receiptItems.forEach(receiptItem -> receiptItem.setReceipt(this));
    }

    public BigInteger getOriginalTotalPrice() {
        return originalTotalPrice.getPrice();
    }

    public BigInteger getMileageUsage() {
        return mileageUsage.getPrice();
    }

    public BigInteger getPurchasedTotalPrice() {
        return purchasedTotalPrice.getPrice();
    }

    public int getTotalQuantity() {
        return totalQuantity.getQuantity();
    }
}
