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
import java.time.LocalDateTime;
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

    @Getter
    @Column(name = "liquor_id", nullable = false)
    private Long liquorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brew_id", nullable = false)
    private LiquorBrew liquorBrew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private LiquorRegion liquorRegion;

    @Column(name = "liquor_name", nullable = false, length = 30)
    @Convert(converter = LiquorNameConverter.class)
    private LiquorName liquorName;

    @Column(name = "liquor_original_price", nullable = false, length = 255)
    @Convert(converter = LiquorPriceConverter.class)

    private LiquorPrice liquorOriginalPrice;

    @Column(name = "liquor_purchased_price", nullable = false, length = 255)
    @Convert(converter = LiquorPriceConverter.class)
    private LiquorPrice liquorPurchasedPrice;

    @Column(name = "liquor_brand", nullable = false, length = 20)
    @Convert(converter = LiquorBrandConverter.class)
    private LiquorBrand liquorBrand;

    @Column(name = "liquor_image_url", nullable = false, length = 255)
    @Convert(converter = LiquorImageUrlConverter.class)
    private LiquorImageUrl liquorImageUrl;

    @Column(name = "liquor_alcohol", nullable = false)
    @Convert(converter = LiquorAlcoholConverter.class)
    private LiquorAlcohol liquorAlcohol;

    @Column(name = "liquor_volume", nullable = false)
    @Convert(converter = LiquorVolumeConverter.class)
    private LiquorVolume liquorVolume;

    @Column(name = "quantity", nullable = false)
    @Convert(converter = ReceiptQuantityConverter.class)
    private ReceiptQuantity quantity;

    @Column(name = "expired_at", nullable = false)
    @Getter
    private LocalDateTime expiredAt;

    private ReceiptItem(
        final Receipt receipt,
        final Long liquorId,
        final LiquorBrew liquorBrew,
        final LiquorRegion liquorRegion,
        final String liquorName,
        final BigInteger liquorOriginalPrice,
        final BigInteger liquorPurchasedPrice,
        final String liquorBrand,
        final String liquorImageUrl,
        final double liquorAlcohol,
        final int liquorVolume,
        final int quantity,
        final LocalDateTime expiredAt
    ) {
        this.receipt = receipt;
        this.liquorId = liquorId;
        this.liquorBrew = liquorBrew;
        this.liquorRegion = liquorRegion;
        this.liquorName = new LiquorName(liquorName);
        this.liquorOriginalPrice = new LiquorPrice(liquorOriginalPrice);
        this.liquorPurchasedPrice = new LiquorPrice(liquorPurchasedPrice);
        this.liquorBrand = new LiquorBrand(liquorBrand);
        this.liquorImageUrl = new LiquorImageUrl(liquorImageUrl);
        this.liquorAlcohol = new LiquorAlcohol(liquorAlcohol);
        this.liquorVolume = new LiquorVolume(liquorVolume);
        this.quantity = new ReceiptQuantity(quantity);
        this.expiredAt = expiredAt;
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
            liquor.getName(),
            liquor.getPrice(),
            liquor.getPrice(),
            liquor.getBrand(),
            liquor.getImageUrl(),
            liquor.getAlcohol(),
            liquor.getVolume(),
            quantity,
            liquor.getExpiredAt()
        );
    }

    public void setReceipt(final Receipt receipt) {
        this.receipt = receipt;
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }

    public String getLiquorBrew() {
        return liquorBrew.getType().toString();
    }

    public String getLiquorRegion() {
        return liquorRegion.getType().toString();
    }

    public String getLiquorName() {
        return liquorName.getName();
    }

    public BigInteger getLiquorOriginalPrice() {
        return liquorOriginalPrice.getPrice();
    }

    public BigInteger getLiquorPurchasedPrice() {
        return liquorPurchasedPrice.getPrice();
    }

    public String getLiquorBrand() {
        return liquorBrand.getBrand();
    }

    public String getLiquorImageUrl() {
        return liquorImageUrl.getImageUrl();
    }

    public Double getLiquorAlcohol() {
        return liquorAlcohol.getAlcohol();
    }

    public Integer getLiquorVolume() {
        return liquorVolume.getVolume();
    }
}
