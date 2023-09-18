package com.woowacamp.soolsool.core.liquor.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.dto.request.LiquorSearchCondition;
import com.woowacamp.soolsool.core.liquor.dto.response.LiquorClickElementDto;
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

    @Test
    @DisplayName("최신순 : 커서 첫번째를 조회하는 테스트")
    void cursorFirstTestByLatest() {
        // given
        LiquorBrew brew = null;
        LiquorRegion region = null;
        LiquorStatus status = null;
        String brand = null;
        // when
        final List<Liquor> liquors = liquorQueryDslRepository
            .getList(new LiquorSearchCondition(region, brew, status, brand),
                Pageable.ofSize(2), null
            );

        // then
        assertThat(liquors).hasSize(2);
    }

    @Test
    @DisplayName("최신순 : 커서를 연속적으로 조회한다 ")
    void cursorClickCountNullTestByLatest() {
        // given
        LiquorBrew brew = null;
        LiquorRegion region = null;
        LiquorStatus status = null;
        String brand = null;

        // when
        List<Liquor> 커서첫번째 = liquorQueryDslRepository
            .getList(new LiquorSearchCondition(region, brew, status, brand),
                Pageable.ofSize(1), null);
        Long 커서첫번째_ID = 커서첫번째.get(커서첫번째.size() - 1).getId();

        List<Liquor> 커서두번째 = liquorQueryDslRepository
            .getList(new LiquorSearchCondition(region, brew, status, brand),
                Pageable.ofSize(1), 커서첫번째_ID);
        Long 커서두번째_ID = 커서두번째.get(커서두번째.size() - 1).getId();

        List<Liquor> 커서세번째 = liquorQueryDslRepository
            .getList(new LiquorSearchCondition(region, brew, status, brand),
                Pageable.ofSize(1), 커서두번째_ID);
        Long 커서세번째_ID = 커서세번째.get(커서세번째.size() - 1).getId();

        // then
        assertThat(커서첫번째).hasSize(1);
        assertThat(커서첫번째_ID).isGreaterThan(커서두번째_ID);
        assertThat(커서두번째).hasSize(1);
        assertThat(커서두번째_ID).isGreaterThan(커서세번째_ID);
    }


    @Test
    @DisplayName("클릭순 + 최신순 : 커서 첫번째를 조회하는 테스트")
    void cursorFirstTestByClickAndLatest() {
        // given
        LiquorBrew brew = null;
        LiquorRegion region = null;
        LiquorStatus status = null;
        String brand = null;
        Long liquorId = null;
        Long clickCount = null;

        // when
        final List<LiquorClickElementDto> 커서첫번째 = liquorQueryDslRepository
            .getListByClick(new LiquorSearchCondition(region, brew, status, brand),
                Pageable.ofSize(1), liquorId, clickCount);

        // then
        assertThat(커서첫번째).hasSize(1);
    }

    @Test
    @DisplayName("클릭순 + 최신순 : 커서 click Count가 null일때, liquorId가 최신순인 것으로 정렬된다." )
    void cursorClickCountNullTestByClickAndLatest() {
        // given
        LiquorBrew brew = null;
        LiquorRegion region = null;
        LiquorStatus status = null;
        String brand = null;
        Long liquorId = 3L;
        Long clickCount = null;

        // when
        List<LiquorClickElementDto> 커서첫번째 = liquorQueryDslRepository
            .getListByClick(new LiquorSearchCondition(region, brew, status, brand),
                Pageable.ofSize(1), liquorId, clickCount);
        Long 커서첫번째_liquorId = 커서첫번째.get(커서첫번째.size() - 1).getId();

        // then
        assertThat(liquorId).isGreaterThan(커서첫번째_liquorId);
    }

    @Test
    @DisplayName("클릭순+최신순 : 커서 liquorId와 clickCount가 not null 일때, 클릭률 순으로, 클릭률 같다면 최신 순으로 정렬" )
    void cursorLiquorIdClickCCountNotNullTestByClickAndLatest(){
        // given
        LiquorBrew brew = null;
        LiquorRegion region = null;
        LiquorStatus status = null;
        String brand = null;
        Long liquorId = 3L;
        Long clickCount = 100L;

        // when
        List<LiquorClickElementDto> 커서첫번째 = liquorQueryDslRepository
            .getListByClick(new LiquorSearchCondition(region, brew, status, brand),
                Pageable.ofSize(1), liquorId, clickCount);
        Long 커서첫번째_liquorId = 커서첫번째.get(커서첫번째.size() - 1).getClickCount();
        Long 커서첫번째_clickCount = 커서첫번째.get(커서첫번째.size() - 1).getClickCount();

        List<LiquorClickElementDto> 커서두번째 = liquorQueryDslRepository
            .getListByClick(new LiquorSearchCondition(region, brew, status, brand),
                Pageable.ofSize(1), 커서첫번째_liquorId, 커서첫번째_clickCount);
        Long 커서두번째_clickCount = 커서두번째.get(커서두번째.size() - 1).getClickCount();

        // then
        assertThat(커서첫번째_clickCount).isGreaterThanOrEqualTo(커서두번째_clickCount);
    }
}
