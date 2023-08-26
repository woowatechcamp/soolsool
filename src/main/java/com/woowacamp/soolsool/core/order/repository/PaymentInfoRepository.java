package com.woowacamp.soolsool.core.order.repository;

import com.woowacamp.soolsool.core.order.domain.PaymentInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {

    @Query("select p from PaymentInfo p inner join Order o on p.orderId = o.id")
    Optional<PaymentInfo> findPaymentInfoByOrderId(final Long orderId);
}
