package com.woowacamp.soolsool.core.liquor.repository;

import static com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType.GYEONGGI_DO;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_REGION_TYPE_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LiquorRegionRepositoryTest {

    @Autowired
    private LiquorRegionRepository liquorRegionRepository;

    @BeforeEach
    void setUpLiquorRegion() {
        Arrays.stream(LiquorRegionType.values())
            .forEach(type -> liquorRegionRepository.save(new LiquorRegion(null, type)));
    }

    @Test
    @DisplayName("LiquorRegionStatus의 name를 가지고 LiquorRegion을 조회한다.")
    void findByLiquorRegionType_type() {
        // given
        LiquorRegion 경기도 = liquorRegionRepository.findByType(GYEONGGI_DO)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_REGION_TYPE_FOUND));

        // when & then
        assertThat(경기도.getType().getName()).isEqualTo(GYEONGGI_DO.getName());
    }
}
