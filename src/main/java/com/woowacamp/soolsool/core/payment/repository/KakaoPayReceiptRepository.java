package com.woowacamp.soolsool.core.payment.repository;

import com.woowacamp.soolsool.core.payment.domain.KakaoPayReceipt;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoPayReceiptRepository extends JpaRepository<KakaoPayReceipt, Long> {

    Optional<KakaoPayReceipt> findByReceiptId(final Long receiptId);
}
