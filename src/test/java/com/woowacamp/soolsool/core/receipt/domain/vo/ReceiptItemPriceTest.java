package com.woowacamp.soolsool.core.receipt.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: ReceiptItemPrice")
class ReceiptItemPriceTest {

    @Test
    @DisplayName("주문서 술 가격을 정상적으로 생성한다.")
    void create() {
        /* given */
        BigInteger price = BigInteger.valueOf(10_000L);

        /* when & then */
        assertThatCode(() -> new ReceiptItemPrice(price))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("주문서 술 가격이 0 미만일 경우 SoolSoolException을 던진다.")
    void createFailWithInvalidPrice() {
        /* given */
        BigInteger price = BigInteger.valueOf(-1L);

        /* when & then */
        assertThatThrownBy(() -> new ReceiptItemPrice(price))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문서 술 가격은 0 미만일 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 술 가격이 null일 경우 SoolSoolException을 던진다.")
    void createFailWithNull() {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new ReceiptItemPrice(null))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문서 술 가격은 null일 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 술 가격이 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        ReceiptItemPrice origin = new ReceiptItemPrice(BigInteger.ONE);
        ReceiptItemPrice same = new ReceiptItemPrice(BigInteger.ONE);
        ReceiptItemPrice different = new ReceiptItemPrice(BigInteger.ZERO);

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );

    }
}
