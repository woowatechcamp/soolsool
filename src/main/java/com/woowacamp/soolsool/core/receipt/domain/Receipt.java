package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptPriceConverter;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptQuantityConverter;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptPrice;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptQuantity;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
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
import lombok.Builder;
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
    @Getter
    private Long id;

    @Column(name = "member_id")
    @Getter
    private Long memberId;

    // TODO : RECEIPTSTATUS 가 이상합니다. cascasdeType =ALl을 설정 안하면 에러가 남. null 값으로.
    @ColumnDefault("1")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_status_id", nullable = false)
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

    @Builder
    public Receipt(
        final Long id,
        final Long memberId,
        final ReceiptStatus receiptStatus,
        final String originalTotalPrice,
        final String mileageUsage,
        final String purchasedTotalPrice,
        final int totalQuantity,
        final List<ReceiptItem> receiptItems
    ) {
        this.id = id;
        this.memberId = memberId;
        this.receiptStatus = receiptStatus;
        this.originalTotalPrice = new ReceiptPrice(new BigInteger(originalTotalPrice));
        this.mileageUsage = new ReceiptPrice(new BigInteger(mileageUsage));
        this.purchasedTotalPrice = new ReceiptPrice(new BigInteger(purchasedTotalPrice));
        this.totalQuantity = new ReceiptQuantity(totalQuantity);
        addReceiptItems(receiptItems);
    }

    public static Receipt of(
        final Long memberId,
        final ReceiptStatus receiptStatus,
        final ReceiptItems receiptItems
    ) {
        return new Receipt(
            null, memberId,
            receiptStatus,
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

    public String getReceiptStatus() {
        return receiptStatus.getType().toString();
    }

    public int getTotalQuantity() {
        return totalQuantity.getQuantity();
    }

    public void updateStatus(final String receiptStatusType) {
        this.receiptStatus = new ReceiptStatus(ReceiptStatusType.valueOf(receiptStatusType));
    }
}