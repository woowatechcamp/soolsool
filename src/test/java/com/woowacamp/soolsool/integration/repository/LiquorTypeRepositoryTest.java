package com.woowacamp.soolsool.integration.repository;

import static com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType.BERRY;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LiquorBrewRepositoryTest {

    @Autowired
    private LiquorBrewRepository liquorBrewRepository;

    @Test
    @DisplayName("LiquorBrew의 name를 가지고 LiquorBrew을 조회한다.")
    void findByLiquorBrew_type() {
        // given
        LiquorBrew 과실주 = liquorBrewRepository
            .findByType(BERRY);

        // when & then
        assertThat(과실주.getType().getName()).isEqualTo(BERRY.getName());
    }
}
