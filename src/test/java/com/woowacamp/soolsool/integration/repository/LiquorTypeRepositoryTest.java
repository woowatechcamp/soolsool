package com.woowacamp.soolsool.integration.repository;

import static com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType.BERRY;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.repository.LiquorTypeRepository;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LiquorTypeRepositoryTest {

    @Autowired
    private LiquorTypeRepository liquorTypeRepository;

    @BeforeEach
    void setUpLiquorType() {
        Arrays.stream(LiquorBrewType.values())
            .forEach(type -> liquorTypeRepository.save(new LiquorType(null, type)));
    }

    @Test
    @DisplayName("LiquorType의 name를 가지고 LiquorType을 조회한다.")
    void findByLiquorType_type() {
        // given
        LiquorType 과실주 = liquorTypeRepository
            .findByType(BERRY)
            .orElseThrow();

        // when & then
        assertThat(과실주.getType().getType()).isEqualTo(BERRY.getType());
    }
}
