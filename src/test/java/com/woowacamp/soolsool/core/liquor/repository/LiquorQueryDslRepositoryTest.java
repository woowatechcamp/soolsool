package com.woowacamp.soolsool.core.liquor.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.dto.liquor.LiquorSearchCondition;
import com.woowacamp.soolsool.global.config.QuerydslConfig;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import({QuerydslConfig.class, LiquorQueryDslRepository.class})
@Sql({
    "/member-type.sql", "/member.sql",
    "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql", "/liquor-ctr.sql",
})
class LiquorQueryDslRepositoryTest {

    @Autowired
    private LiquorQueryDslRepository liquorQueryDslRepository;
    @Autowired
    private LiquorRegionRepository liquorRegionRepository;
    @Autowired
    private LiquorStatusRepository liquorStatusRepository;
    @Autowired
    private LiquorBrewRepository liquorBrewRepository;

    @Test
    @DisplayName("커서 첫번째를 조회하는 테스트")
    void cursorFirstTest() {
        // given
        LiquorBrew brew = liquorBrewRepository.findByType(LiquorBrewType.SOJU).get();
        LiquorRegion region = liquorRegionRepository.findByType(LiquorRegionType.GYEONGGI_DO).get();
        LiquorStatus status = liquorStatusRepository.findByType(LiquorStatusType.ON_SALE).get();
        String brand = "롯데";

        // when
        List<Liquor> 커서첫번째 = liquorQueryDslRepository
            .getList(new LiquorSearchCondition(region, brew, status, brand),
                Pageable.ofSize(10), null
            );

        // then
        assertThat(커서첫번째).hasSize(1);
    }

    @Test
    @DisplayName("커서 두번째를 조회하는 테스트")
    void curSecondTest() {
        // given
        LiquorBrew brew = liquorBrewRepository.findByType(LiquorBrewType.SOJU).get();
        LiquorRegion region = liquorRegionRepository.findByType(LiquorRegionType.GYEONGGI_DO).get();
        LiquorStatus status = liquorStatusRepository.findByType(LiquorStatusType.ON_SALE).get();
        String brand = "롯데";

        // when
        List<Liquor> 커서두번째 = liquorQueryDslRepository
            .getList(new LiquorSearchCondition(region, brew, status, brand),
                Pageable.ofSize(10), 1L
            );

        // then
        assertThat(커서두번째).isEmpty();
    }
}
