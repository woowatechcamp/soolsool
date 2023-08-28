package com.woowacamp.soolsool.core.liquor.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.LiquorCtr;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql({
    "/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"
})
@DisplayName("단위 테스트: LiquorCtrRepository")
class LiquorCtrRepositoryTest {

    @Autowired
    LiquorCtrRepository liquorCtrRepository;

    @Test
    @DisplayName("LiquorId로 LiquorCtr을 조회한다.")
    void findByLiquorId() {
        /* given */
        Long 새로 = 1L;

        /* when */
        final LiquorCtr liquorCtr = liquorCtrRepository.findByLiquorId(새로)
            .orElseThrow(() -> new IllegalArgumentException("테스트 데이터가 없습니다"));

        /* then */
        assertThat(liquorCtr.getCtr()).isEqualTo(0.5);
    }

    @Test
    @DisplayName("LiquorId 리스트로 LiquorCtr 리스트를 조회한다.")
    void findAllByLiquorIdIn() {
        /* given */
        Long 새로 = 1L;
        Long 얼음딸기주 = 3L;
        List<Long> 술_목록 = List.of(새로, 얼음딸기주);

        /* when */
        final List<LiquorCtr> liquorCtrs = liquorCtrRepository.findAllByLiquorIdIn(술_목록);

        /* then */
        assertThat(liquorCtrs).hasSize(2);
    }
}
