package com.woowacamp.soolsool.core.order.domain;

import com.woowacamp.soolsool.core.order.domain.converter.OrderPriceConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderQuantityConverter;
import com.woowacamp.soolsool.core.order.domain.vo.OrderPrice;
import com.woowacamp.soolsool.core.order.domain.vo.OrderQuantity;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatus;
import com.woowacamp.soolsool.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "total_price", nullable = false)
    @Convert(converter = OrderPriceConverter.class)
    private OrderPrice totalPrice;

    @Column(name = "total_quantity", nullable = false)
    @Convert(converter = OrderQuantityConverter.class)
    private OrderQuantity totalQuantity;

    @Builder
    public Order(
        final Long memberId,
        final OrderStatus status,
        final OrderPrice totalPrice,
        final OrderQuantity totalQuantity
    ) {
        this.memberId = memberId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }
}
