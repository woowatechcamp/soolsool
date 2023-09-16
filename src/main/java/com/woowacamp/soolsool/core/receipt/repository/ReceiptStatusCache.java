package com.woowacamp.soolsool.core.receipt.repository;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptStatus;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiptStatusCache {

    private final ReceiptStatusRepository receiptStatusRepository;

    @Cacheable(value = "receiptStatus", key = "#receiptStatusType",unless = "#result==null")
    public Optional<ReceiptStatus> findByType(final ReceiptStatusType receiptStatusType) {
        log.info("ReceiptStatusCache {}", receiptStatusType);
        return receiptStatusRepository.findByType(receiptStatusType);
    }
}
