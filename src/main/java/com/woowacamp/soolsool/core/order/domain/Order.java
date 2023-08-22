package com.woowacamp.soolsool.core.order.domain;

import com.woowacamp.soolsool.core.receipt.domain.Receipt;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @Builder
    public Order(
        final Long memberId,
        final OrderStatus orderStatus,
        final Receipt receipt
    ) {
        this.memberId = memberId;
        this.status = orderStatus;
        this.receipt = receipt;
    }

    public static Order of(
        final Long memberId,
        final OrderStatus orderStatus,
        final Receipt receipt
    ) {
        return new Order(memberId, orderStatus, receipt);
    }

    public void updateStatus(final OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
