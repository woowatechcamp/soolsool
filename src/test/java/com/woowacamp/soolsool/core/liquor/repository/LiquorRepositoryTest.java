package com.woowacamp.soolsool.core.liquor.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorAlcohol;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrand;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorImageUrl;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorName;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorPrice;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStock;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorVolume;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class LiquorRepositoryTest {

    private static final LiquorBrewType LIQUOR_BREW_TYPE = LiquorBrewType.SOJU;
    private static final LiquorRegionType LIQUOR_REGION_TYPE = LiquorRegionType.CHUNGCHEONGNAM_DO;
    private static final LiquorStatusType LIQUOR_STATUS_TYPE = LiquorStatusType.ON_SALE;

    @Autowired
    private LiquorRepository liquorRepository;

    @Autowired
    private LiquorTypeRepository liquorTypeRepository;

    @Autowired
    private LiquorRegionRepository liquorRegionRepository;

    @Autowired
    private LiquorStatusRepository liquorStatusRepository;

    private LiquorType liquorType;
    private LiquorRegion liquorRegion;
    private LiquorStatus liquorStatus;

    @BeforeEach
    void setUp() {
        Arrays.stream(LiquorBrewType.values())
                .forEach(brewType -> liquorTypeRepository.save(new LiquorType(null, brewType)));
        Arrays.stream(LiquorRegionType.values())
                .forEach(regionType -> liquorRegionRepository.save(new LiquorRegion(null, regionType)));
        Arrays.stream(LiquorStatusType.values())
                .forEach(statusType -> liquorStatusRepository.save(new LiquorStatus(null, statusType)));

        liquorType = liquorTypeRepository.findByType(LIQUOR_BREW_TYPE)
                .orElseThrow();
        liquorRegion = liquorRegionRepository.findByType(LIQUOR_REGION_TYPE)
                .orElseThrow();
        liquorStatus = liquorStatusRepository.findByType(LIQUOR_STATUS_TYPE)
                .orElseThrow();
    }

    @Test
    @DisplayName("id로 Liquor를 조회한다.")
    void findById() {
        // given
        Liquor liquor = new Liquor(
                liquorType,
                liquorRegion,
                liquorStatus,
                new LiquorName("소주"),
                new LiquorPrice(new BigInteger("3000")),
                new LiquorBrand("브랜드"),
                new LiquorImageUrl("/soju.jpg"),
                new LiquorStock(100),
                new LiquorAlcohol(17.2),
                new LiquorVolume(330)
        );
        Liquor saved = liquorRepository.save(liquor);

        // when
        Optional<Liquor> optionalResult = liquorRepository.findById(saved.getId());

        // then
        assertThat(optionalResult).isPresent();
    }

    @Test
    @DisplayName("Liquor 리스트를 조회한다.")
    void findAll() {
        // given
        Liquor soju = new Liquor(
                liquorType,
                liquorRegion,
                liquorStatus,
                new LiquorName("소주"),
                new LiquorPrice(new BigInteger("3000")),
                new LiquorBrand("브랜드"),
                new LiquorImageUrl("/soju.jpg"),
                new LiquorStock(100),
                new LiquorAlcohol(17.2),
                new LiquorVolume(330)
        );
        Liquor savedSoju = liquorRepository.save(soju);

        Liquor beer = new Liquor(
                liquorType,
                liquorRegion,
                liquorStatus,
                new LiquorName("맥주"),
                new LiquorPrice(new BigInteger("5000")),
                new LiquorBrand("오비"),
                new LiquorImageUrl("/beer.jpg"),
                new LiquorStock(200),
                new LiquorAlcohol(5.2),
                new LiquorVolume(500)
        );
        Liquor savedBeer = liquorRepository.save(beer);

        Pageable defaultPageable = PageRequest.of(0, 10);

        // when
        Page<Liquor> liquors = liquorRepository.findAll(defaultPageable);

        // then
        assertThat(liquors.getContent().stream().map(Liquor::getId).collect(Collectors.toList()))
                .containsExactly(savedSoju.getId(), savedBeer.getId());
    }
}
