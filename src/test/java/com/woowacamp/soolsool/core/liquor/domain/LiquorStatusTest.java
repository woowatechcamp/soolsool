package com.woowacamp.soolsool.core.liquor.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: LiquorStatus")
class LiquorStatusTest {

    @Test
    @DisplayName("술 상태를 정상적으로 생성한다.")
    void create() {
        /* given */
        LiquorStatusType type = LiquorStatusType.ON_SALE;

        /* when */
        LiquorStatus status = new LiquorStatus(type);

        /* then */
        assertThat(status.getType()).isEqualTo(type);
    }
}
