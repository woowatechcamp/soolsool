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

@DisplayName("단위 테스트: ReceiptItemBrand")
class ReceiptItemBrandTest {

    @Test
    @DisplayName("주문서 술 브랜드를 정상적으로 생성한다.")
    void create() {
        /* given */
        String name = "우아한";

        /* when & then */
        assertThatCode(() -> new ReceiptItemBrand(name))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("주문서 술 브랜드가 null 혹는 공백일 경우 SoolSoolException를 던진다.")
    void createFailWithNullOrEmpty(String name) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new ReceiptItemBrand(name))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문서 술 브랜드는 null이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 술 브랜드가 20자를 초과할 경우 SoolSoolException를 던진다.")
    void createFailInvalidLength() {
        /* given */
        String name = "소".repeat(21);

        /* when & then */
        assertThatThrownBy(() -> new ReceiptItemBrand(name))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문서 술 브랜드는 20자보다 길 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 술 브랜드가 동일하면 동일한 객체가다.")
    void equalsAndHashCode() {
        /* given */
        ReceiptItemBrand origin = new ReceiptItemBrand("우아한");
        ReceiptItemBrand same = new ReceiptItemBrand("우아한");
        ReceiptItemBrand different = new ReceiptItemBrand("형제들");

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}

