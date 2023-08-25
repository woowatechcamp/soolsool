package com.woowacamp.soolsool.core.receipt.repository;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptStatus;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiptStatusCache {

    private final ReceiptStatusRepository receiptStatusRepository;

    @Cacheable(value = "receiptStatus", key = "#receiptStatusType")
    public Optional<ReceiptStatus> findByType(final ReceiptStatusType receiptStatusType) {
        return receiptStatusRepository.findByType(receiptStatusType);
    }

}
