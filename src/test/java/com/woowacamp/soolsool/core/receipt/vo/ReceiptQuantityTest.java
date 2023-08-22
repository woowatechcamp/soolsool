package com.woowacamp.soolsool.core.receipt.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptQuantity;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("주문서 개수 단위 테스트")
class ReceiptQuantityTest {

    @Test
    @DisplayName("주문서 개수를 정상적으로 생성한다.")
    void create() {
        /* given */
        int stock = 777;

        /* when & then */
        assertThatCode(() -> new ReceiptQuantity(stock))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("주문서 개수가 0 미만일 경우 ShoppingException을 던진다.")
    void createFailWithInvalidStock() {
        /* given */
        int stock = -1;

        /* when & then */
        assertThatThrownBy(() -> new ReceiptQuantity(stock))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문서 개수는 0 미만일 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 개수가 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        ReceiptQuantity origin = new ReceiptQuantity(777);
        ReceiptQuantity same = new ReceiptQuantity(777);
        ReceiptQuantity different = new ReceiptQuantity(123);

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}
