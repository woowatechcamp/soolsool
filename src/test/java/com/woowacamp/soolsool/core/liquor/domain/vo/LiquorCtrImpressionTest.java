package com.woowacamp.soolsool.core.liquor.domain.vo;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: LiquorCtrImpression")
class LiquorCtrImpressionTest {

    @Test
    @DisplayName("LiquorCtrImpression을 정상 생성한다.")
    void create() {
        /* given */


        /* when */
        assertThatCode(() -> new LiquorCtrImpression(1L))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("노출 수가 null이면 SoolSoolException을 던진다.")
    void createFailWithNull() {
        /* given */


        /* when & then */
        assertThatCode(() -> new LiquorCtrImpression(null))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("노출수는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("노출 수가 음수이면 SoolSoolException을 던진다.")
    void createFailWithLessThanZero() {
        /* given */


        /* when & then */
        assertThatCode(() -> new LiquorCtrImpression(-1L))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("노출수는 음수일 수 없습니다.");
    }
}
