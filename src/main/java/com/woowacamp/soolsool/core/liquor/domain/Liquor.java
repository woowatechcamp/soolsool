package com.woowacamp.soolsool.core.liquor.domain;

import com.woowacamp.soolsool.global.common.BaseEntity;
import javax.persistence.Column;
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
    @JoinColumn(name = "type_id")
    private LiquorType liquorType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private LiquorRegion liquorRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private LiquorStatus liquorStatus;

    @Column(name = "name", nullable = false, length = 30)
    private LiquorName name;

    @Column(name = "price", nullable = false, length = 255)
    private LiquorPrice price;

    @Column(name = "brand", nullable = false, length = 20)
    private LiquorBrand brand;

    @Column(name = "image_url", nullable = false, length = 255)
    private LiquorImageUrl imageUrl;

    @Column(name = "stock", nullable = false)
    private LiquorStock stock;

    @Column(name = "alcohol", nullable = false)
    private LiquorAlcohol alcohol;

    @Column(name = "volume", nullable = false)
    private LiquorVolume volume;

    // TODO: 필드가 너무 많은데 Liquor 정보를 모으는 VO가 필요할 것 같다
    @Builder
    public Liquor(
        final LiquorType liquorType,
        final LiquorRegion liquorRegion,
        final LiquorStatus liquorStatus,
        final LiquorName name,
        final LiquorPrice price,
        final LiquorBrand brand,
        final LiquorImageUrl imageUrl,
        final LiquorStock stock,
        final LiquorAlcohol alcohol,
        final LiquorVolume volume
    ) {
        this.liquorType = liquorType;
        this.liquorRegion = liquorRegion;
        this.liquorStatus = liquorStatus;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.alcohol = alcohol;
        this.volume = volume;
    }
}
