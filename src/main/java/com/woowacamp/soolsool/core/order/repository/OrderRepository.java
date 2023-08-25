package com.woowacamp.soolsool.core.order.repository;

import com.woowacamp.soolsool.core.order.domain.Order;
import java.util.Optional;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.receipt r join fetch r.receiptItems ri"
        + " where o.id = :orderId")
    Optional<Order> findOrderById(@Param("orderId") final Long orderId);

    @BatchSize(size = 100)
    @Query("select distinct o from Order o inner join o.receipt r inner join r.receiptItems ri"
        + " where o.memberId = :memberId")
    Page<Order> findAllByMemberId(@Param("memberId") final Long memberId, final Pageable pageable);
}
