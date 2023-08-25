package com.woowacamp.soolsool.core.receipt.repository;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptStatus;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptStatusRepository extends JpaRepository<ReceiptStatus, Long> {

    @Cacheable(value = "receiptStatus", key = "#receiptStatusType")
    Optional<ReceiptStatus> findByType(final ReceiptStatusType receiptStatusType);
}
