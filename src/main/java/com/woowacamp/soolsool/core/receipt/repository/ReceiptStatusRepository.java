package com.woowacamp.soolsool.core.receipt.repository;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptStatus;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptStatusRepository extends JpaRepository<ReceiptStatus, Long> {

    Optional<ReceiptStatus> findByType(final ReceiptStatusType receiptStatusType);
}
