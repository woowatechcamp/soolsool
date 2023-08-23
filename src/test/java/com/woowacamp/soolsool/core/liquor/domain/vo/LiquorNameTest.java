package com.woowacamp.soolsool.core.liquor.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("단위 테스트: LiquorName")
class LiquorNameTest {

    @Test
    @DisplayName("술 이름을 정상적으로 생성한다.")
    void create() {
        /* given */
        String name = "마싯는 소주";

        /* when & then */
        assertThatCode(() -> new LiquorName(name))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("술 이름이 null 혹은 공백일 경우 SoolSoolException을 던진다.")
    void createFailWithNullOrEmpty(String name) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new LiquorName(name))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("술 이름은 null이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("술 이름이 30자를 초과할 경우 SoolSoolException을 던진다.")
    void createFailInvalidLength() {
        /* given */
        String name = "소".repeat(31);

        /* when & then */
        assertThatThrownBy(() -> new LiquorName(name))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("술 이름은 30자보다 길 수 없습니다.");
    }

    @Test
    @DisplayName("술 이름이 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        LiquorName origin = new LiquorName("마싯는 소주");
        LiquorName same = new LiquorName("마싯는 소주");
        LiquorName different = new LiquorName("맛없는 소주");

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}
