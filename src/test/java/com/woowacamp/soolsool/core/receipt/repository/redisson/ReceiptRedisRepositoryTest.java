package com.woowacamp.soolsool.core.receipt.repository.redisson;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("통합 테스트 : ReceiptRedisRepository")
class ReceiptRedisRepositoryTest {

    @Autowired
    ReceiptRedisRepository receiptRedisRepository;

    @Test
    @DisplayName("5분 후 파기되는 주문서를 발행한다.")
    void addExpiredEvent() {
        // given
        Long receiptId = 1L;
        Long memberId = 1L;

        // when & then
        assertDoesNotThrow(()
            -> receiptRedisRepository.addExpiredEvent(receiptId, memberId, 5));
    }
}
