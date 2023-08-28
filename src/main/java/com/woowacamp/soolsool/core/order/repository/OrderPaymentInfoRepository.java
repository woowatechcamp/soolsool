package com.woowacamp.soolsool.core.order.repository;

import com.woowacamp.soolsool.core.order.domain.OrderPaymentInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentInfoRepository extends JpaRepository<OrderPaymentInfo, Long> {

    @Query("select p from OrderPaymentInfo p inner join Order o on p.orderId = o.id and o.id = :orderId")
    Optional<OrderPaymentInfo> findPaymentInfoByOrderId(final Long orderId);
}
