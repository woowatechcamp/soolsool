package com.woowacamp.soolsool.core.liquor.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: LiquorRegion")
class LiquorRegionTest {

    @Test
    @DisplayName("술 단위를 정상적으로 생성한다.")
    void create() {
        /* given */
        LiquorRegionType type = LiquorRegionType.GYEONGGI_DO;

        /* when */
        LiquorRegion liquorRegion = new LiquorRegion(type);

        /* then */
        assertThat(liquorRegion.getType()).isEqualTo(type);
    }
}
