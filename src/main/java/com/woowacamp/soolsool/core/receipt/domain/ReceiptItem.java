package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptItemAlcoholConverter;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptItemBrandConverter;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptItemBrewConverter;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptItemImageUrlConverter;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptItemNameConverter;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptItemPriceConverter;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptItemRegionConverter;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptItemVolumeConverter;
import com.woowacamp.soolsool.core.receipt.domain.converter.ReceiptQuantityConverter;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemAlcohol;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemBrand;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemBrew;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemImageUrl;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemName;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemPrice;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemQuantity;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemRegion;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptItemVolume;
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
import lombok.NonNull;

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

    @Column(name = "liquor_brew", nullable = false)
    @Convert(converter = ReceiptItemBrewConverter.class)
    private ReceiptItemBrew liquorBrew;

    @Column(name = "liquor_region", nullable = false)
    @Convert(converter = ReceiptItemRegionConverter.class)
    private ReceiptItemRegion liquorRegion;

    @Column(name = "liquor_name", nullable = false)
    @Convert(converter = ReceiptItemNameConverter.class)
    private ReceiptItemName liquorName;

    @Column(name = "liquor_original_price", nullable = false, length = 255)
    @Convert(converter = ReceiptItemPriceConverter.class)
    private ReceiptItemPrice liquorOriginalPrice;

    @Column(name = "liquor_purchased_price", nullable = false, length = 255)
    @Convert(converter = ReceiptItemPriceConverter.class)
    private ReceiptItemPrice liquorPurchasedPrice;

    @Column(name = "liquor_brand", nullable = false, length = 20)
    @Convert(converter = ReceiptItemBrandConverter.class)
    private ReceiptItemBrand liquorBrand;

    @Column(name = "liquor_image_url", nullable = false, length = 255)
    @Convert(converter = ReceiptItemImageUrlConverter.class)
    private ReceiptItemImageUrl liquorImageUrl;

    @Column(name = "liquor_alcohol", nullable = false)
    @Convert(converter = ReceiptItemAlcoholConverter.class)
    private ReceiptItemAlcohol liquorAlcohol;

    @Column(name = "liquor_volume", nullable = false)
    @Convert(converter = ReceiptItemVolumeConverter.class)
    private ReceiptItemVolume liquorVolume;

    @Column(name = "quantity", nullable = false)
    @Convert(converter = ReceiptQuantityConverter.class)
    private ReceiptItemQuantity quantity;

    @Builder
    public ReceiptItem(
        final Receipt receipt,
        @NonNull final Long liquorId,
        @NonNull final String liquorBrew,
        @NonNull final String liquorRegion,
        @NonNull final String liquorName,
        @NonNull final String liquorOriginalPrice,
        @NonNull final String liquorPurchasedPrice,
        @NonNull final String liquorBrand,
        @NonNull final String liquorImageUrl,
        @NonNull final Double liquorAlcohol,
        @NonNull final Integer liquorVolume,
        @NonNull final Integer quantity
    ) {
        this.receipt = receipt;
        this.liquorId = liquorId;
        this.liquorBrew = new ReceiptItemBrew(liquorBrew);
        this.liquorRegion = new ReceiptItemRegion(liquorRegion);
        this.liquorName = new ReceiptItemName(liquorName);
        this.liquorOriginalPrice = new ReceiptItemPrice(new BigInteger(liquorOriginalPrice));
        this.liquorPurchasedPrice = new ReceiptItemPrice(new BigInteger(liquorPurchasedPrice));
        this.liquorBrand = new ReceiptItemBrand(liquorBrand);
        this.liquorImageUrl = new ReceiptItemImageUrl(liquorImageUrl);
        this.liquorAlcohol = new ReceiptItemAlcohol(liquorAlcohol);
        this.liquorVolume = new ReceiptItemVolume(liquorVolume);
        this.quantity = new ReceiptItemQuantity(quantity);
    }

    public static ReceiptItem of( // 생성자로 바꾸기
        final Liquor liquor,
        final int quantity
    ) {
        return ReceiptItem.builder()
            .liquorId(liquor.getId())
            .liquorBrew(liquor.getBrew().getType().getName())
            .liquorRegion(liquor.getRegion().getType().getName())
            .liquorName(liquor.getName())
            .liquorOriginalPrice(liquor.getPrice().toString())
            .liquorPurchasedPrice(liquor.getPrice().toString())
            .liquorBrand(liquor.getBrand())
            .liquorImageUrl(liquor.getImageUrl())
            .liquorAlcohol(liquor.getAlcohol())
            .liquorVolume(liquor.getVolume())
            .quantity(quantity)
            .build();
    }

    public String getReceiptItemBrew() {
        return liquorBrew.getBrew();
    }

    public String getReceiptItemRegion() {
        return liquorRegion.getRegion();
    }

    public String getReceiptItemName() {
        return liquorName.getName();
    }

    public BigInteger getReceiptItemOriginalPrice() {
        return liquorOriginalPrice.getPrice();
    }

    public BigInteger getReceiptItemPurchasedPrice() {
        return liquorPurchasedPrice.getPrice();
    }

    public String getReceiptItemBrand() {
        return liquorBrand.getBrand();
    }

    public String getReceiptItemImageUrl() {
        return liquorImageUrl.getImageUrl();
    }

    public Double getReceiptItemAlcohol() {
        return liquorAlcohol.getAlcohol();
    }

    public Integer getReceiptItemVolume() {
        return liquorVolume.getVolume();
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }

    public void setReceipt(final Receipt receipt) {
        this.receipt = receipt;
    }
}
