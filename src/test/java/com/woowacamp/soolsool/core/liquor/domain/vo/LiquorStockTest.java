package com.woowacamp.soolsool.core.liquor.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("술 재고 단위 테스트")
class LiquorStockTest {

    @Test
    @DisplayName("술 재고를 정상적으로 생성한다.")
    void create() {
        /* given */
        int stock = 777;

        /* when & then */
        assertThatCode(() -> new LiquorStock(stock))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("술 재고가 0 미만일 경우 SoolSoolException을 던진다.")
    void createFailWithInvalidStock() {
        /* given */
        int stock = -1;

        /* when & then */
        assertThatThrownBy(() -> new LiquorStock(stock))
                .isExactlyInstanceOf(SoolSoolException.class)
                .hasMessage("술 재고는 0 미만일 수 없습니다.");
    }

    @Test
    @DisplayName("술 재고가 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        LiquorStock origin = new LiquorStock(777);
        LiquorStock same = new LiquorStock(777);
        LiquorStock different = new LiquorStock(123);

        /* when & then */
        assertAll(
                () -> assertThat(origin).isEqualTo(same),
                () -> assertThat(origin).hasSameHashCodeAs(same),
                () -> assertThat(origin).isNotEqualTo(different),
                () -> assertThat(origin).doesNotHaveSameHashCodeAs(different)
        );
    }
}
