package com.woowacamp.soolsool.core.order.domain;

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
    private OrderItemType liquorType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_region")
    private OrderItemRegion liquorRegion;

    @Column(name = "liquor_name", length = 30)
    private OrderItemName name;

    @Column(name = "liquor_original_price", length = 255)
    private OrderItemPrice originalPrice;

    @Column(name = "liquor_purchased_price", length = 255)
    private OrderItemPrice purchasedPrice;

    @Column(name = "liquor_brand", length = 20)
    private OrderItemBrand brand;

    @Column(name = "liquor_image_url", length = 255)
    private OrderItemImageUrl imageUrl;

    @Column(name = "liquor_alcohol")
    private OrderItemAlcohol alcohol;

    @Column(name = "liquor_volume")
    private OrerItemVolume volume;

    @Column(name = "quantity")
    private OrderItemQuantity quantity;

    // TODO: 주문서 객체로 변경?
    // TODO: 필드가 너무 많다. -> VO로 묶기 필요
    @Builder
    public OrderItem(
        final Order order,
        final OrderItemType liquorType,
        final OrderItemRegion liquorRegion,
        final OrderItemName name,
        final OrderItemPrice originalPrice,
        final OrderItemPrice purchasedPrice,
        final OrderItemBrand brand,
        final OrderItemImageUrl imageUrl,
        final OrderItemAlcohol alcohol,
        final OrerItemVolume volume,
        final OrderItemQuantity quantity
    ) {
        this.order = order;
        this.liquorType = liquorType;
        this.liquorRegion = liquorRegion;
        this.name = name;
        this.originalPrice = originalPrice;
        this.purchasedPrice = purchasedPrice;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.alcohol = alcohol;
        this.volume = volume;
        this.quantity = quantity;
    }
}
