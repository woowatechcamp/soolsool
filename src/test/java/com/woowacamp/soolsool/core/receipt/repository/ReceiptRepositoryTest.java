package com.woowacamp.soolsool.core.receipt.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptStatus;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql({
    "/member-type.sql", "/member.sql",
    "/liquor-type.sql", "/liquor.sql",
    "/cart-item.sql",
    "/receipt-type.sql", "/receipt.sql"
})
@DisplayName("통합 테스트: ReceiptRepository")
class ReceiptRepositoryTest {

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    ReceiptStatusRepository receiptStatusRepository;

    @Test
    @DisplayName("주문서 상태를 변경한다.")
    void updateReceiptStatus() {
        /* given */
        Receipt receipt = receiptRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("주문서가 존재하지 않습니다."));
        ReceiptStatus receiptStatus = receiptStatusRepository
            .findByType(ReceiptStatusType.CANCELED)
            .orElseThrow(() -> new RuntimeException("주문서 상태가 존재하지 않습니다."));

        /* when */
        receipt.updateStatus(receiptStatus);

        /* then */
        Receipt result = receiptRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("주문서가 존재하지 않습니다."));
        assertThat(result.getReceiptStatus()).isEqualTo("CANCELED");
    }
}
