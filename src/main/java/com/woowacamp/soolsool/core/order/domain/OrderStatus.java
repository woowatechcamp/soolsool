package com.woowacamp.soolsool.core.order.domain;

import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "order_status")
@Getter
@RequiredArgsConstructor
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 20)
    private OrderStatusType type;

    public OrderStatus(final OrderStatusType type) {
        this.type = type;
    }

    public static OrderStatus getByDefaultStatus() {
        return new OrderStatus(OrderStatusType.COMPLETED);
    }
}
