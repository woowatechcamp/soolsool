package com.woowacamp.soolsool.core.liquor.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: LiquorStocks")
class LiquorStocksTest {

    @Test
    @DisplayName("상품 재고들을 생성한다.")
    void create() {
        /* given */
        LiquorStock 재고1 = mock(LiquorStock.class);
        LiquorStock 재고2 = mock(LiquorStock.class);
        LiquorStock 재고3 = mock(LiquorStock.class);
        List<LiquorStock> liquorStocks = List.of(재고1, 재고2, 재고3);


        /* when & then */
        assertThatCode(() -> new LiquorStocks(liquorStocks))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("상품 재고가 없을 경우 SoolSoolException을 던진다.")
    void createFailWithEmpty() {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new LiquorStocks(Collections.emptyList()))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주류 재고가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("다른 상품 재고가 섞여있을 경우 SoolSoolException을 던진다.")
    void createFailWithDifferent() {
        /* given */
        LiquorStock 재고1 = mock(LiquorStock.class);
        when(재고1.getLiquorId()).thenReturn(1L);
        LiquorStock 재고2 = mock(LiquorStock.class);
        when(재고2.getLiquorId()).thenReturn(2L);
        List<LiquorStock> liquorStocks = List.of(재고1, 재고2);

        /* when & then */
        assertThatThrownBy(() -> new LiquorStocks(liquorStocks))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("동일한 주류가 아닌 재고가 포함되어 있습니다.");
    }

    @Test
    @DisplayName("재고보다 더 많이 주문할 경우 SoolSoolException을 던진다.")
    void decrease() {
        /* given */
        LiquorStock 재고1 = mock(LiquorStock.class);
        when(재고1.getStock()).thenReturn(0);
        LiquorStocks liquorStocks = new LiquorStocks(List.of(재고1));

        /* when & then */
        assertThatThrownBy(() -> liquorStocks.decreaseStock(12345))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("주류 재고가 부족합니다.");
    }
}
