package com.woowacamp.soolsool.core.liquor.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("술 도수 단위 테스트")
class LiquorAlcoholTest {

    @Test
    @DisplayName("술 도수를 정상적으로 생성한다.")
    void create() {
        /* given */
        double alcohol = 17.2;

        /* when & then */
        assertThatCode(() -> new LiquorAlcohol(alcohol)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("술 도수가 0 이상이 아닌 경우 SoolSoolException을 던진다.")
    void createFailWithIsNotGreaterThanZero() {
        /* given */


        /* when & then */
        assertThatCode(() -> new LiquorAlcohol(-1.2)).isExactlyInstanceOf(SoolSoolException.class)
                .hasMessage("술 도수는 0 이상 실수여야 합니다.");
    }

    @Test
    @DisplayName("술 도수가 같다면 같은 객체이다.")
    void equalsAndHashcode() {
        /* given */
        LiquorAlcohol origin = new LiquorAlcohol(12.3);
        LiquorAlcohol same = new LiquorAlcohol(12.3);
        LiquorAlcohol different = new LiquorAlcohol(77.7);

        /* when & then */
        assertAll(
                () -> assertThat(origin).isEqualTo(same),
                () -> assertThat(origin).hasSameHashCodeAs(same),
                () -> assertThat(origin).isNotEqualTo(different),
                () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}
