package com.woowacamp.soolsool.core.receipt.event.listener;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import com.woowacamp.soolsool.core.receipt.event.ReceiptExpiredEvent;
import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReceiptEventListener {

    private final ReceiptService receiptService;

    @Async
    @EventListener
    public void expireReceipt(final ReceiptExpiredEvent event) {
        receiptService.modifyReceiptStatus(
            event.getMemberId(),
            event.getReceiptId(),
            ReceiptStatusType.EXPIRED
        );

        log.info("Member {}'s Receipt {} Expired", event.getMemberId(), event.getReceiptId());
    }
}
