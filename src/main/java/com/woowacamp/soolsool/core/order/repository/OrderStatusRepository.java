package com.woowacamp.soolsool.core.order.repository;

import com.woowacamp.soolsool.core.order.domain.OrderStatus;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    @Cacheable(value = "orderStatus", key = "#type")
    Optional<OrderStatus> findByType(final OrderStatusType type);
}
