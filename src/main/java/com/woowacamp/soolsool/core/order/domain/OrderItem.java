package com.woowacamp.soolsool.core.order.domain;

import com.woowacamp.soolsool.core.order.domain.converter.OrderItemAlcoholConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderItemBrandConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderItemImageUrlConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderItemNameConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderItemPriceConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderItemQuantityConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderItemRegionConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderItemTypeConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderItemVolumeConverter;
import com.woowacamp.soolsool.core.order.domain.vo.OrderItemAlcohol;
import com.woowacamp.soolsool.core.order.domain.vo.OrderItemBrand;
import com.woowacamp.soolsool.core.order.domain.vo.OrderItemImageUrl;
import com.woowacamp.soolsool.core.order.domain.vo.OrderItemName;
import com.woowacamp.soolsool.core.order.domain.vo.OrderItemPrice;
import com.woowacamp.soolsool.core.order.domain.vo.OrderItemQuantity;
import com.woowacamp.soolsool.core.order.domain.vo.OrderItemRegion;
import com.woowacamp.soolsool.core.order.domain.vo.OrderItemType;
import com.woowacamp.soolsool.core.order.domain.vo.OrerItemVolume;
import com.woowacamp.soolsool.global.common.BaseEntity;
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

    @Column(name = "liquor_category")
    @Convert(converter = OrderItemTypeConverter.class)
    private OrderItemType liquorType;

    @Column(name = "liquor_region", length = 30)
    @Convert(converter = OrderItemRegionConverter.class)
    private OrderItemRegion liquorRegion;

    @Column(name = "liquor_name", length = 30)
    @Convert(converter = OrderItemNameConverter.class)
    private OrderItemName name;

    @Column(name = "liquor_original_price", length = 255)
    @Convert(converter = OrderItemPriceConverter.class)
    private OrderItemPrice originalPrice;

    @Column(name = "liquor_purchased_price", length = 255)
    @Convert(converter = OrderItemPriceConverter.class)
    private OrderItemPrice purchasedPrice;

    @Column(name = "liquor_brand", length = 20)
    @Convert(converter = OrderItemBrandConverter.class)
    private OrderItemBrand brand;

    @Column(name = "liquor_image_url", length = 255)
    @Convert(converter = OrderItemImageUrlConverter.class)
    private OrderItemImageUrl imageUrl;

    @Column(name = "liquor_alcohol")
    @Convert(converter = OrderItemAlcoholConverter.class)
    private OrderItemAlcohol alcohol;

    @Column(name = "liquor_volume")
    @Convert(converter = OrderItemVolumeConverter.class)
    private OrerItemVolume volume;

    @Column(name = "quantity")
    @Convert(converter = OrderItemQuantityConverter.class)
    private OrderItemQuantity quantity;

    // TODO: 주문서 객체로 변경?
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
