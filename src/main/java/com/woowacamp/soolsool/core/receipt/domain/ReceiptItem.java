package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorAlcoholConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorBrandConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorImageUrlConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorNameConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorPriceConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorVolumeConverter;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorAlcohol;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrand;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorImageUrl;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorName;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorPrice;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorVolume;
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

    @Getter
    @Column(name = "liquor_id", nullable = false)
    private Long liquorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brew_id", nullable = false)
    @Getter
    private LiquorBrew liquorBrew;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    @Getter
    private LiquorRegion liquorRegion;
    @Column(name = "name", nullable = false, length = 30)
    @Convert(converter = LiquorNameConverter.class)
    private LiquorName liquorName;
    @Column(name = "original_price", nullable = false, length = 255)
    @Convert(converter = LiquorPriceConverter.class)
    private LiquorPrice liquorOriginalPrice;
    @Column(name = "purchased_price", nullable = false, length = 255)
    @Convert(converter = LiquorPriceConverter.class)
    private LiquorPrice liquorPurchasedPrice;
    @Column(name = "brand", nullable = false, length = 20)
    @Convert(converter = LiquorBrandConverter.class)
    private LiquorBrand liquorBrand;
    @Column(name = "image_url", nullable = false, length = 255)
    @Convert(converter = LiquorImageUrlConverter.class)
    private LiquorImageUrl liquorImageUrl;
    @Column(name = "alcohol", nullable = false)
    @Convert(converter = LiquorAlcoholConverter.class)
    private LiquorAlcohol liquorAlcohol;

    @Column(name = "volume", nullable = false)
    @Convert(converter = LiquorVolumeConverter.class)
    private LiquorVolume liquorVolume;

    @Column(name = "quantity", nullable = false)
    @Convert(converter = ReceiptQuantityConverter.class)
    private ReceiptQuantity quantity;


    @Builder
    public ReceiptItem(final Receipt receipt, final Long liquorId,
        final LiquorBrew liquorBrew,
        final LiquorRegion liquorRegion, final LiquorName liquorName,
        final LiquorPrice liquorOriginalPrice,
        final LiquorPrice liquorPurchasedPrice, final LiquorBrand liquorBrand,
        final LiquorImageUrl liquorImageUrl,
        final LiquorAlcohol liquorAlcohol, final LiquorVolume liquorVolume,
        final ReceiptQuantity quantity) {

        this.receipt = receipt;
        this.liquorId = liquorId;
        this.liquorBrew = liquorBrew;
        this.liquorRegion = liquorRegion;
        this.liquorName = liquorName;
        this.liquorOriginalPrice = liquorOriginalPrice;
        this.liquorPurchasedPrice = liquorPurchasedPrice;
        this.liquorBrand = liquorBrand;
        this.liquorImageUrl = liquorImageUrl;
        this.liquorAlcohol = liquorAlcohol;
        this.liquorVolume = liquorVolume;
        this.quantity = quantity;
    }

    public static ReceiptItem of( // 생성자로 바꾸기
        final Liquor liquor,
        final int quantity
    ) {
        return new ReceiptItem(
            null,
            liquor.getId(),
            liquor.getBrew(),
            liquor.getRegion(),
            new LiquorName(liquor.getName()),
            new LiquorPrice(liquor.getPrice()),
            new LiquorPrice(liquor.getPrice()),
            new LiquorBrand(liquor.getBrand()),
            new LiquorImageUrl(liquor.getImageUrl()),
            new LiquorAlcohol(liquor.getAlcohol()),
            new LiquorVolume(liquor.getVolume()),
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
        //return liquor.getPrice().multiply(new BigInteger(String.valueOf(getQuantity())));
        return new BigInteger(String.valueOf(getQuantity()));
    }
}
