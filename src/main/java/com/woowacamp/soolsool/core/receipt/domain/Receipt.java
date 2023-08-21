package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.core.receipt.converter.ReceiptPriceConverter;
import com.woowacamp.soolsool.core.receipt.converter.ReceiptQuantityConverter;
import com.woowacamp.soolsool.core.receipt.vo.ReceiptPrice;
import com.woowacamp.soolsool.core.receipt.vo.ReceiptQuantity;
import com.woowacamp.soolsool.global.common.BaseEntity;
import com.woowacamp.soolsool.global.exception.GlobalErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_status_id", nullable = false)
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

    @Column(name = "expired_date")
    private LocalDateTime expirationDate;

    @Getter
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<ReceiptItem> receiptItems = new ArrayList<>();

    private void validateIsNotNullableCategory(final Object... objects) {
        if (Arrays.stream(objects).anyMatch(Objects::isNull)) {
            throw new SoolSoolException(GlobalErrorCode.NO_CONTENT);
        }
    }

    @Builder
    public Receipt(final Long memberId,
        final ReceiptStatus receiptStatus,
        final String originalTotalPrice,
        final String mileageUsage,
        final String purchasedTotalPrice,
        final int totalQuantity,
        final LocalDateTime expirationDate,
        final List<ReceiptItem> receiptItems
    ) {
        this(
            null, memberId, receiptStatus, originalTotalPrice,
            mileageUsage, purchasedTotalPrice,
            totalQuantity, expirationDate, receiptItems
        );
    }


    public Receipt(
        final Long id,
        final Long memberId,
        final ReceiptStatus receiptStatus,
        final String originalTotalPrice,
        final String mileageUsage,
        final String purchasedTotalPrice,
        final int totalQuantity,
        final LocalDateTime expirationDate,
        final List<ReceiptItem> receiptItems
    ) {
        validateIsNotNullableCategory(receiptStatus);

        this.id = id;
        this.memberId = memberId;
        this.receiptStatus = receiptStatus;
        this.originalTotalPrice = new ReceiptPrice(new BigInteger(originalTotalPrice));
        this.mileageUsage = new ReceiptPrice(new BigInteger(mileageUsage));
        this.purchasedTotalPrice = new ReceiptPrice(new BigInteger(purchasedTotalPrice));
        this.totalQuantity = new ReceiptQuantity(totalQuantity);
        this.expirationDate = expirationDate;
        this.receiptItems = receiptItems;
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
