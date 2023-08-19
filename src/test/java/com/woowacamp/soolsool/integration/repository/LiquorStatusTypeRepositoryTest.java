package com.woowacamp.soolsool.integration.repository;

import static com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType.ON_SALE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("술 판매 상태 repository 통합 테스트")
class LiquorStatusTypeRepositoryTest {

    @Autowired
    private LiquorStatusRepository liquorStatusRepository;

    @Test
    @DisplayName("LiquorStatus의 name를 가지고 LiquorStatus을 조회한다.")
    void findByLiquorRegionType_type() {
        // given
        LiquorStatus 판매중 = liquorStatusRepository
            .findByType(ON_SALE).orElseThrow();

        // when & then
        assertThat(판매중.getType().getStatus()).isEqualTo(ON_SALE.getStatus());
    }
}
