package com.woowacamp.soolsool.core.receipt.repository;

import static com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType.INPROGRESS;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.config.CacheTestConfig;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptStatus;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/receipt-type.sql")
@DisplayName("통합 테스트: ReceiptStatusRepository")
@Import({ReceiptStatusCache.class, CacheTestConfig.class})
class ReceiptStatusRepositoryTest {

    @Autowired
    ReceiptStatusCache receiptStatusRepository;

    @Test
    @DisplayName("주문서 상태를 주문서 상태 타입으로 검색한다.")
    void findByType() {
        /* given */


        /* when */
        final Optional<ReceiptStatus> receiptStatus =
            receiptStatusRepository.findByType(INPROGRESS);

        /* then */
        assertThat(receiptStatus).isPresent();
    }
}
