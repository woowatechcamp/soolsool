package com.woowacamp.soolsool.core.liquor.repository;

import static com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType.GYEONGGI_DO;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/liquor-type.sql")
@DisplayName("술 지역 repository 통합 테스트")
class LiquorRegionRepositoryTest {

    @Autowired
    private LiquorRegionRepository liquorRegionRepository;

    @Test
    @DisplayName("LiquorRegionStatus의 name를 가지고 LiquorRegion을 조회한다.")
    void findByLiquorRegionType_type() {
        // given
        LiquorRegion 경기도 = liquorRegionRepository.findByType(GYEONGGI_DO)
            .orElseThrow(() -> new SoolSoolException(LiquorErrorCode.NOT_LIQUOR_REGION_FOUND));

        // when & then
        assertThat(경기도.getType().getName()).isEqualTo(GYEONGGI_DO.getName());
    }
}
