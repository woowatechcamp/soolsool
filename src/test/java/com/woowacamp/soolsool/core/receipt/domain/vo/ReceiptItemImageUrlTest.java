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

@DisplayName("단위 테스트: ReceiptItemImageUrl")
class ReceiptItemImageUrlTest {

    @Test
    @DisplayName("주문서 술 이미지 경로를 정상적으로 생성한다.")
    void create() {
        /* given */
        String imageUrl = "soju.png";

        /* when & then */
        assertThatCode(() -> new ReceiptItemImageUrl(imageUrl))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("주문서 술 이미지 경로가 null 혹은 공백일 경우 SoolSoolException을 던진다.")
    void createFailWithNullOrEmpty(String imageUrl) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new ReceiptItemImageUrl(imageUrl))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문서 술 이미지 경로는 null이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 술 이미지 경로가 255자 초과일 경우 SoolSoolException을 던진다.")
    void createFailInvalidLength() {
        /* given */
        String imageUrl = "a".repeat(256);

        /* when & then */
        assertThatThrownBy(() -> new ReceiptItemImageUrl(imageUrl))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주문서 술 이미지 경로는 255자보다 길 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 술 이미지 경로가 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        ReceiptItemImageUrl origin = new ReceiptItemImageUrl("soju.png");
        ReceiptItemImageUrl same = new ReceiptItemImageUrl("soju.png");
        ReceiptItemImageUrl different = new ReceiptItemImageUrl("not_soju.png");

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}

