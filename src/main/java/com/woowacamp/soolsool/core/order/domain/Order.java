package com.woowacamp.soolsool.core.order.domain;

import com.woowacamp.soolsool.core.order.domain.converter.OrderMileageUsageConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderPriceConverter;
import com.woowacamp.soolsool.core.order.domain.converter.OrderQuantityConverter;
import com.woowacamp.soolsool.core.order.domain.vo.OrderMileageUsage;
import com.woowacamp.soolsool.core.order.domain.vo.OrderPrice;
import com.woowacamp.soolsool.core.order.domain.vo.OrderQuantity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id", nullable = false)
    private OrderStatus status;

    @Column(name = "original_total_price", nullable = false)
    @Convert(converter = OrderPriceConverter.class)
    private OrderPrice originalTotalPrice;

    @Column(name = "mileage_usage", nullable = false)
    @Convert(converter = OrderMileageUsageConverter.class)
    private OrderMileageUsage mileageUsage;

    @Column(name = "purchased_total_price", nullable = false)
    @Convert(converter = OrderPriceConverter.class)
    private OrderPrice purchasedTotalPrice;

    @Column(name = "total_quantity", nullable = false)
    @Convert(converter = OrderQuantityConverter.class)
    private OrderQuantity totalQuantity;

    @Builder
    public Order(
        final Long memberId,
        final OrderStatus status,
        final String originalTotalPrice,
        final String mileageUsage,
        final String purchasedTotalPrice,
        final Integer totalQuantity
    ) {
        this.memberId = memberId;
        this.status = status;
        this.originalTotalPrice = new OrderPrice(new BigInteger(originalTotalPrice));
        this.mileageUsage = new OrderMileageUsage(new BigInteger(mileageUsage));
        this.purchasedTotalPrice = new OrderPrice(new BigInteger(purchasedTotalPrice));
        this.totalQuantity = new OrderQuantity(totalQuantity);
    }
}
