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

    @Query(value = "select cast(count(o.id) as double) / nullif((select count(sub_ri.id) "
        + "                                                      from receipt_items sub_ri "
        + "                                                      where sub_ri.liquor_id = :liquorId"
        + "                                                      ), 0) "
        + "from orders o inner join receipt_items ri on o.receipt_id = ri.receipt_id "
        + "where ri.liquor_id = :liquorId"
        + "      and o.order_status_id = (select os.id from order_status os where os.name = 'COMPLETED')",
        nativeQuery = true
    )
    Optional<Double> findOrderRatioByLiquorId(final Long liquorId);

    Optional<Order> findByIdAndMemberId(final Long orderId, final Long memberId);
}
