package com.woowacamp.soolsool.core.liquor.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("술 종류 단위 테스트")
class LiquorBrewTest {

    @Test
    @DisplayName("술 종류를 정상적으로 생성한다.")
    void create() {
        /* given */
        final LiquorBrewType type = LiquorBrewType.BERRY;

        /* when */
        final LiquorBrew brew = new LiquorBrew(type);

        /* then */
        assertThat(brew.getType()).isEqualTo(type);
    }
}
