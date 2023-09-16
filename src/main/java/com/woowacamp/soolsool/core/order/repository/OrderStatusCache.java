package com.woowacamp.soolsool.core.order.repository;

import com.woowacamp.soolsool.core.order.domain.OrderStatus;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusCache {

    private final OrderStatusRepository orderStatusRepository;

    @Cacheable(value = "orderStatus", key = "#type", unless = "#result==null")
    public Optional<OrderStatus> findByType(final OrderStatusType type) {
        log.info("OrderStatusCache {}", type);
        return orderStatusRepository.findByType(type);
    }
}
