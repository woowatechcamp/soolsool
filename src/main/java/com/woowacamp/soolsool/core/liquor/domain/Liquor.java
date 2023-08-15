package com.woowacamp.soolsool.core.liquor.domain;

import com.woowacamp.soolsool.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "liquors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Liquor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "legion")
    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private String price;

    @Column(name = "brand")
    private String brand;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "alcohol")
    private String alcohol;

    @Column(name = "volume")
    private String volume;

    @Column(name = "stock")
    private String stock;
}
