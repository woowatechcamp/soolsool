package com.woowacamp.soolsool.core.receipt.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("단위 테스트: ReceiptItemRegion")
class ReceiptItemRegionTest {

    @Test
    @DisplayName("주문서 술 지역을 정상적으로 생성한다.")
    void create() {
        /* given */
        String name = "SOJU";

        /* when & then */
        assertThatCode(() -> new ReceiptItemRegion(name))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("주문서 술 지역이 null 혹는 공백일 경우 SoolSoolException를 던진다.")
    void createFailWithNullOrEmpty(String name) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new ReceiptItemRegion(name))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문서 술 지역은 null이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 술 지역이 20자를 초과할 경우 SoolSoolException를 던진다.")
    void createFailInvalidLength() {
        /* given */
        String name = "소".repeat(21);

        /* when & then */
        assertThatThrownBy(() -> new ReceiptItemRegion(name))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문서 술 지역은 20자보다 길 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 술 지역이 동일하면 동일한 객체가다.")
    void equalsAndHashCode() {
        /* given */
        ReceiptItemRegion origin = new ReceiptItemRegion("우아한");
        ReceiptItemRegion same = new ReceiptItemRegion("우아한");
        ReceiptItemRegion different = new ReceiptItemRegion("형제들");

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}
