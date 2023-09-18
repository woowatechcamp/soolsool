package com.woowacamp.soolsool.core.receipt.repository.redisson;

import com.woowacamp.soolsool.core.receipt.event.ReceiptExpiredEvent;
import com.woowacamp.soolsool.core.receipt.event.ReceiptRemoveEvent;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ReceiptRedisRepository {

    private static final String RECEIPT_EXPIRED_KEY = "RECEIPT_EXPIRED";

    private final RMapCache<Long, Long> receiptCache;

    public ReceiptRedisRepository(
        final RedissonClient redissonClient,
        final ApplicationEventPublisher publisher
    ) {
        redissonClient.getMapCache(RECEIPT_EXPIRED_KEY)
            .addListenerAsync((EntryExpiredListener<Long, Long>) event
                -> publisher.publishEvent(new ReceiptExpiredEvent(event.getKey(), event.getValue()))
            );

        this.receiptCache = redissonClient.getMapCache(RECEIPT_EXPIRED_KEY);
    }

    public void addExpiredEvent(final Long receiptId, final Long memberId, final long minutes) {
        receiptCache.put(receiptId, memberId, minutes, TimeUnit.MINUTES);

        log.info("Complete to save Member {}'s Receipt {} to Redis", memberId, receiptCache);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void removeReceipt(final ReceiptRemoveEvent event) {
        receiptCache.remove(event.getReceiptId());
    }
}
