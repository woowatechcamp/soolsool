package com.woowacamp.soolsool.core.liquor.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("술 용량 단위 테스트")
class LiquorVolumeTest {

    @Test
    @DisplayName("술 용량을 정상적으로 생성한다.")
    void create() {
        /* given */
        int volume = 777;

        /* when & then */
        assertThatCode(() -> new LiquorVolume(volume))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("술 용량이 0 미만일 경우 SoolSoolException을 던진다.")
    void createFailWithInvalidVolume() {
        /* given */
        int volume = -1;

        /* when & then */
        assertThatThrownBy(() -> new LiquorVolume(volume))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("술 용량은 0 미만일 수 없습니다.");
    }

    @Test
    @DisplayName("술 용량이 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        LiquorVolume origin = new LiquorVolume(777);
        LiquorVolume same = new LiquorVolume(777);
        LiquorVolume different = new LiquorVolume(123);

        /* when & then */
        assertAll(
            () -> assertThat(origin).isEqualTo(same),
            () -> assertThat(origin).hasSameHashCodeAs(same),
            () -> assertThat(origin).isNotEqualTo(different),
            () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}

