package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptItemPriceConverter;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptQuantityConverter;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemPrice;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemQuantity;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import com.woowacamp.soolsool.global.common.BaseEntity;
import java.math.BigInteger;
import java.time.LocalDateTime;
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

@Entity
@Table(name = "receipts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receipt extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Getter
    private Long id;

    @Column(name = "member_id")
    @Getter
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_status_id", nullable = false)
    private ReceiptStatus receiptStatus;

    @Column(name = "original_total_price", nullable = false)
    @Convert(converter = ReceiptItemPriceConverter.class)
    private ReceiptItemPrice originalTotalPrice;

    @Column(name = "mileage_usage", nullable = false)
    @Convert(converter = ReceiptItemPriceConverter.class)
    private ReceiptItemPrice mileageUsage;

    @Column(name = "purchased_total_price", nullable = false)
    @Convert(converter = ReceiptItemPriceConverter.class)
    private ReceiptItemPrice purchasedTotalPrice;

    @Column(name = "total_quantity", nullable = false)
    @Convert(converter = ReceiptQuantityConverter.class)
    private ReceiptItemQuantity totalQuantity;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expiredDate = LocalDateTime.now().plusDays(30);

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<ReceiptItem> receiptItems = new ArrayList<>();

    @Builder
    public Receipt(
        final Long memberId,
        final ReceiptStatus receiptStatus,
        final ReceiptItemPrice originalTotalPrice,
        final ReceiptItemPrice mileageUsage,
        final ReceiptItemPrice purchasedTotalPrice,
        final ReceiptItemQuantity totalQuantity,
        final List<ReceiptItem> receiptItems
    ) {
        this.memberId = memberId;
        this.receiptStatus = receiptStatus;
        this.originalTotalPrice = originalTotalPrice;
        this.mileageUsage = mileageUsage;
        this.purchasedTotalPrice = purchasedTotalPrice;
        this.totalQuantity = totalQuantity;
        addReceiptItems(receiptItems);
    }

    public void addReceiptItems(final List<ReceiptItem> receiptItems) {
        this.receiptItems.addAll(receiptItems);
        receiptItems.forEach(receiptItem -> receiptItem.setReceipt(this));
    }

    public void updateStatus(final ReceiptStatusType type) {
        this.receiptStatus.updateStatus(type);
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
}
