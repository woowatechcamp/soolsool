package com.woowacamp.soolsool.core.receipt.event.listener;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import com.woowacamp.soolsool.core.receipt.event.ReceiptExpiredEvent;
import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiptExpiredEventListener {

    private final ReceiptService receiptService;

    @EventListener
    public void expiredListener(final ReceiptExpiredEvent event) {
        receiptService.modifyReceiptStatus(
            event.getMemberId(), event.getReceiptId(), ReceiptStatusType.EXPIRED
        );

        log.info("Member {}'s Receipt {} Expired", event.getMemberId(), event.getReceiptId());
    }
}
