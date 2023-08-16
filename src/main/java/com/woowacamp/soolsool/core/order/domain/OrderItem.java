package com.woowacamp.soolsool.core.order.domain;

import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_category")
    private LiquorType liquorType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_region")
    private LiquorRegion liquorRegion;

    @Column(name = "liquor_name", length = 30)
    private String name;

    // TODO: total price는 price와 quantity로 구할 수 있지 않나?
    // TODO: colum 순서

    @Column(name = "liquor_price", length = 255)
    private String price;

    @Column(name = "liquor_brand", length = 20)
    private String brand;

    @Column(name = "liquor_image_url", length = 255)
    private String imageUrl;

    @Column(name = "liquor_alcohol")
    private String alcohol;

    @Column(name = "liquor_volume")
    private String volume;

    // TODO: 얘는 OrderItem만의 데이터니까 liquor 빼는거 어때
    @Column(name = "quantity")
    private String quantity;

}
