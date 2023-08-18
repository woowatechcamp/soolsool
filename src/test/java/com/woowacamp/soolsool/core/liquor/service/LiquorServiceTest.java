package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_REGION_TYPE_FOUND;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_STATUS_TYPE_FOUND;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorTypeRepository;
import com.woowacamp.soolsool.global.exception.LiquorErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
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
        SaveLiquorRequest saveLiquorRequest = new SaveLiquorRequest(
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
    @DisplayName("liquor의 region가 없는 경우, 예외를 반환한다.")
    void saveLiquorTestWithRegionFails() {
        // given
        SaveLiquorRequest saveLiquorRequest = new SaveLiquorRequest(
            "SOJU",
            "GYEONGGI_DO2",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);
        // when & then
        assertThatCode(() -> liquorService.saveLiquor(saveLiquorRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_LIQUOR_REGION_TYPE_FOUND.getMessage());
    }

    @Test
    @DisplayName("liquor의 status가 없는 경우, 예외를 반환한다.")
    void saveLiquorTestWithStatusails() {
        // given
        SaveLiquorRequest saveLiquorRequest = new SaveLiquorRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE2",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);
        // when & then
        assertThatCode(() -> liquorService.saveLiquor(saveLiquorRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_LIQUOR_STATUS_TYPE_FOUND.getMessage());
    }

    @Test
    @DisplayName("liquor의 status가 없는 경우, 예외를 반환한다.")
    void saveLiquorTestWithTypeFails() {
        // given
        SaveLiquorRequest saveLiquorRequest = new SaveLiquorRequest(
                "SOJU",
                "GYEONGGI_DO",
                "ON_SALE2",
                "새로",
                "3000",
                "브랜드",
                "/url",
                100, 12.0,
                300);
        // when & then
        assertThatCode(() -> liquorService.saveLiquor(saveLiquorRequest))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(NOT_LIQUOR_STATUS_TYPE_FOUND.getMessage());
    }

    @Test
    @DisplayName("liquor가 없는 경우, 예외를 반환한다.")
    void liquorDetailTestWithNotFound() {
        // given


        // when & then
        assertThatCode(() -> liquorService.liquorDetail(99999L))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(LiquorErrorCode.NOT_LIQUOR_FOUND.getMessage());
    }
}
