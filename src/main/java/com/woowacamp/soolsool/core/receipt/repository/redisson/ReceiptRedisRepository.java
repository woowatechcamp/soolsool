package com.woowacamp.soolsool.core.receipt.repository.redisson;

import com.woowacamp.soolsool.core.receipt.event.ReceiptExpiredEvent;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReceiptRedisRepository {

    private static final String RECEIPT_EXPIRED_KEY = "RECEIPT_EXPIRED";

    private final RedissonClient redissonClient;

    public ReceiptRedisRepository(
        final RedissonClient redissonClient,
        final ApplicationEventPublisher publisher
    ) {
        redissonClient.getMapCache(RECEIPT_EXPIRED_KEY)
            .addListenerAsync((EntryExpiredListener<Long, Long>) event
                -> publisher.publishEvent(new ReceiptExpiredEvent(event.getKey(), event.getValue()))
            );

        this.redissonClient = redissonClient;
    }

    public void addExpiredEvent(final Long receiptId, final Long memberId, final long minutes) {
        final RMapCache<Long, Long> receiptCache = redissonClient.getMapCache(RECEIPT_EXPIRED_KEY);

        receiptCache.put(receiptId, memberId, minutes, TimeUnit.MINUTES);

        log.info("Complete to save Member {}'s Receipt {} to Redis", memberId, receiptCache);
    }
}
