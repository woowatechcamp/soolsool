package com.woowacamp.soolsool.core.liquor.domain.vo;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: LiquorCtrClick")
class LiquorCtrClickTest {

    @Test
    @DisplayName("LiquorCtrClick을 정상 생성한다.")
    void create() {
        /* given */


        /* when & then */
        assertThatCode(() -> new LiquorCtrClick(1L))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("클릭 수가 null이면 SoolSoolException을 던진다.")
    void createFailWithNull() {
        /* given */


        /* when & then */
        assertThatCode(() -> new LiquorCtrClick(null))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("클릭수는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("클릭 수가 음수이면 SoolSoolException을 던진다.")
    void createFailWithLessThanZero() {
        /* given */


        /* when & then */
        assertThatCode(() -> new LiquorCtrClick(-1L))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("클릭수는 음수일 수 없습니다.");
    }
}
