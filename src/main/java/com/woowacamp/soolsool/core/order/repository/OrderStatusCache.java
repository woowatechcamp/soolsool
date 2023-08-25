package com.woowacamp.soolsool.core.order.repository;

import com.woowacamp.soolsool.core.order.domain.OrderStatus;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusCache {

    private final OrderStatusRepository orderStatusRepository;

    @Cacheable(value = "orderStatus", key = "#type")
    public Optional<OrderStatus> findByType(final OrderStatusType type) {
        return orderStatusRepository.findByType(type);
    }
}
