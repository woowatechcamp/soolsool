package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptQuantityConverter;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptQuantity;
import com.woowacamp.soolsool.global.common.BaseEntity;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "receipt_items")

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    @Getter
    private Receipt receipt;

//    @JoinColumn(name = "liquor_id", nullable = false)
//    @Getter
//    private Liquor liquor;

    private Long liquorId;
    private String liquorBrew;
    private String liquorRegion;
    private String liquorName;
    private String liquorOriginalPrice;
    private String liquorPurchasedPrice;
    private String liquorBrand;
    private String liquorImageUrl;
    private String liquorAlcohol;
    private String liquorVolume;

    @Column(name = "quantity", nullable = false)
    @Convert(converter = ReceiptQuantityConverter.class)
    private ReceiptQuantity quantity;

    @Builder
    public ReceiptItem(
        final Receipt receipt,
        final Liquor liquor,
        final ReceiptQuantity quantity
    ) {
        this.receipt = receipt;
        this.liquor = liquor;
        this.quantity = quantity;
    }

    public static ReceiptItem of( // 생성자로 바꾸기
        final Liquor liquor,
        final int quantity
    ) {
        return new ReceiptItem(
            null,
            liquor,
            new ReceiptQuantity(quantity)
        );
    }

    public void setReceipt(final Receipt receipt) {
        this.receipt = receipt;
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }

    public BigInteger getTotalAmount() {
        return liquor.getPrice().multiply(new BigInteger(String.valueOf(getQuantity())));
    }
}
