package com.woowacamp.soolsool.integration.repository;

import static com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType.ON_SALE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LiquorStatusTypeRepositoryTest {

    @Autowired
    private LiquorStatusRepository liquorStatusRepository;

    @BeforeEach
    void setUpLiquorStatus() {
        Arrays.stream(LiquorStatusType.values())
            .forEach(type -> liquorStatusRepository.save(new LiquorStatus(null, type)));
    }

    @Test
    @DisplayName("LiquorStatus의 name를 가지고 LiquorStatus을 조회한다.")
    void findByLiquorRegionType_type() {
        // given
        LiquorStatus 판매중 = liquorStatusRepository.findByType(ON_SALE);
        // when & then
        assertThat(판매중.getType().getStatus()).isEqualTo(ON_SALE.getStatus());
    }
}
