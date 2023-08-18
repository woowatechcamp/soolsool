package com.woowacamp.soolsool.core.liquor.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorTypeRepository;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LiquorServiceTest {

    @Autowired
    private LiquorRepository liquorRepository;
    @Autowired
    private LiquorRegionRepository liquorRegionRepository;
    @Autowired
    private LiquorStatusRepository liquorStatusRepository;
    @Autowired
    private LiquorTypeRepository liquorTypeRepository;
    private LiquorService liquorService;

    @BeforeEach
    public void setUp() {
        liquorService = new LiquorService(liquorRepository,
            liquorStatusRepository, liquorRegionRepository, liquorTypeRepository);

        Arrays.stream(LiquorStatusType.values())
            .forEach(type -> liquorStatusRepository.save(new LiquorStatus(null, type)));
        Arrays.stream(LiquorRegionType.values())
            .forEach(type -> liquorRegionRepository.save(new LiquorRegion(null, type)));
        Arrays.stream(LiquorBrewType.values())
            .forEach(type -> liquorTypeRepository.save(new LiquorType(null, type)));
    }


    @Test
    @DisplayName("liquor를 저장한다.")
    void saveLiquorTest() {
        // given
        LiquorSaveRequest saveLiquorRequest = new LiquorSaveRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);
        // when & then
        assertThatCode(() -> liquorService.saveLiquor(saveLiquorRequest))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("liquor를 수정한다.")
    void modifyLiquorTestWithSuccess() {
        // given
        LiquorSaveRequest saveLiquorRequest = new LiquorSaveRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);
        Long saveLiquorId = liquorService.saveLiquor(saveLiquorRequest);
        LiquorModifyRequest liquorModifyRequest = new LiquorModifyRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "안동소주",
            "3000",
            "브랜드",
            "soolsool.png",
            100,
            12.0,
            1
        );
        // when
        liquorService.modifyLiquor(saveLiquorId, liquorModifyRequest);

        // then
        Liquor findLiquor = liquorRepository.findById(saveLiquorId).orElseThrow();
        assertThat(findLiquor.getName()).extracting("name")
            .isEqualTo(liquorModifyRequest.getName());
    }

}
