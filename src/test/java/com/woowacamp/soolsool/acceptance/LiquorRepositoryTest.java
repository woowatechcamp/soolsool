package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.config.QuerydslTestConfig;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSearchCondition;
import com.woowacamp.soolsool.core.liquor.repository.LiquorQueryDslRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@Import({QuerydslTestConfig.class, LiquorQueryDslRepository.class})
class LiquorRepositoryTest {

    @Autowired
    private LiquorQueryDslRepository liquorQueryDslRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("커서 첫번째를 조회하는 테스트")
    void cursorFirstTest() {
        // given
        LiquorBrew brew = new LiquorBrew(LiquorBrewType.SOJU);
        LiquorRegion region = new LiquorRegion(LiquorRegionType.GYEONGGI_DO);
        LiquorStatus status = new LiquorStatus(LiquorStatusType.ON_SALE);
        entityManager.persist(brew);
        entityManager.persist(region);
        entityManager.persist(status);

        String name = "마싯는 소주";
        String price = "10000";
        String brand = "우아한";
        String imageUrl = "soju.png";
        double alcohol = 17.2;
        int volume = 500;
        Liquor liquor = Liquor.builder()
            .brew(brew)
            .region(region)
            .status(status)
            .name(name)
            .price(price)
            .brand(brand)
            .imageUrl(imageUrl)
            .alcohol(alcohol)
            .volume(volume)
            .build();
        entityManager.persist(liquor);

        // when
        final List<LiquorElementResponse> 커서첫번째 = liquorQueryDslRepository
            .getList(new LiquorSearchCondition(Optional.of(region), Optional.of(brew),
                    Optional.of(status), brand),
                Pageable.ofSize(10), null);
        // then
        assertThat(커서첫번째).hasSize(1);
    }

    @Test
    @DisplayName("커서 두번째를 조회하는 테스트")
    void curSecondTest() {
        // given
        LiquorBrew brew = new LiquorBrew(LiquorBrewType.SOJU);
        LiquorRegion region = new LiquorRegion(LiquorRegionType.GYEONGGI_DO);
        LiquorStatus status = new LiquorStatus(LiquorStatusType.ON_SALE);
        entityManager.persist(brew);
        entityManager.persist(region);
        entityManager.persist(status);

        String name = "마싯는 소주";
        String price = "10000";
        String brand = "우아한";
        String imageUrl = "soju.png";
        double alcohol = 17.2;
        int volume = 500;
        Liquor liquor = Liquor.builder()
            .brew(brew)
            .region(region)
            .status(status)
            .name(name)
            .price(price)
            .brand(brand)
            .imageUrl(imageUrl)
            .alcohol(alcohol)
            .volume(volume)
            .build();
        entityManager.persist(liquor);

        // when
        List<LiquorElementResponse> 커서두번째 = liquorQueryDslRepository
            .getList(
                new LiquorSearchCondition(
                    Optional.of(region), Optional.of(brew),
                    Optional.of(status), brand
                ),
                Pageable.ofSize(10), 1L
            );

        // then
        assertThat(커서두번째).isEmpty();
    }
}
