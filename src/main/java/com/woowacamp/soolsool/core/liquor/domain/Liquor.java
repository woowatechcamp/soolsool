package com.woowacamp.soolsool.core.liquor.domain;

import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorAlcoholConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorBrandConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorImageUrlConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorNameConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorPriceConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorStockConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorVolumeConverter;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorAlcohol;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrand;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorImageUrl;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorName;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorPrice;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStock;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorVolume;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
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
@Table(name = "liquors")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Liquor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private LiquorType liquorType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private LiquorRegion liquorRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private LiquorStatus liquorStatus;

    @Column(name = "name", nullable = false, length = 30)
    @Convert(converter = LiquorNameConverter.class)
    private LiquorName name;

    @Column(name = "price", nullable = false, length = 255)
    @Convert(converter = LiquorPriceConverter.class)
    private LiquorPrice price;

    @Column(name = "brand", nullable = false, length = 20)
    @Convert(converter = LiquorBrandConverter.class)
    private LiquorBrand brand;

    @Column(name = "image_url", nullable = false, length = 255)
    @Convert(converter = LiquorImageUrlConverter.class)
    private LiquorImageUrl imageUrl;

    @Column(name = "stock", nullable = false)
    @Convert(converter = LiquorStockConverter.class)
    private LiquorStock stock;

    @Column(name = "alcohol", nullable = false)
    @Convert(converter = LiquorAlcoholConverter.class)
    private LiquorAlcohol alcohol;

    @Column(name = "volume", nullable = false)
    @Convert(converter = LiquorVolumeConverter.class)
    private LiquorVolume volume;

    @Builder
    public Liquor(
        final LiquorType liquorType,
        final LiquorRegion liquorRegion,
        final LiquorStatus liquorStatus,
        final String name,
        final String price,
        final String brand,
        final String imageUrl,
        final int stock,
        final Double alcohol,
        final int volume
    ) {
        this.liquorType = liquorType;
        this.liquorRegion = liquorRegion;
        this.liquorStatus = liquorStatus;
        this.name = new LiquorName(name);
        this.price = new LiquorPrice(new BigInteger(price));
        this.brand = new LiquorBrand(brand);
        this.imageUrl = new LiquorImageUrl(imageUrl);
        this.stock = new LiquorStock(stock);
        this.alcohol = new LiquorAlcohol(alcohol);
        this.volume = new LiquorVolume(volume);
    }

    public void update(
        final LiquorType modifyLiquorType,
        final LiquorRegion modifyLiquorRegion,
        final LiquorStatus modifyLiquorStatus,
        final LiquorModifyRequest request
    ) {
        this.liquorType = modifyLiquorType;
        this.liquorRegion = modifyLiquorRegion;
        this.liquorStatus = modifyLiquorStatus;
        this.name = new LiquorName(request.getName());
        this.price = new LiquorPrice(new BigInteger(request.getPrice()));
        this.brand = new LiquorBrand(request.getBrand());
        this.imageUrl = new LiquorImageUrl(request.getImageUrl());
        this.stock = new LiquorStock(request.getStock());
        this.alcohol = new LiquorAlcohol(request.getAlcohol());
        this.volume = new LiquorVolume(request.getVolume());
    }
}
