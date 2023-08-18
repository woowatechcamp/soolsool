package com.woowacamp.soolsool.integration.service;

import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_FOUND;
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
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
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
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
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
        assertThatCode(() -> liquorService.saveLiquor(liquorSaveRequest))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("liquor를 수정한다.")
    void modifyLiquorTest() {
        // given
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);
        final Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);
        LiquorModifyRequest liquorModifyRequest = new LiquorModifyRequest(
            "BERRY",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로2",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300
        );
        // when
        liquorService.modifyLiquor(saveLiquorId, liquorModifyRequest);

        // then
        final Liquor liquor = liquorRepository.findById(saveLiquorId).orElseThrow();
        assertThat(liquor.getName()).extracting("name")
            .isEqualTo(liquorModifyRequest.getName());
        assertThat(liquor.getName()).extracting("name")
            .isNotEqualTo(liquorSaveRequest.getName());
    }

    @Test
    @DisplayName("liquor Id가 존재하지 않을 때, 수정 시 에러를 반환한다.")
    void modifyLiquorTestFailWithNoExistId() {
        // given
        final long LIQUOR_ID = 2L;
        LiquorModifyRequest liquorModifyRequest = new LiquorModifyRequest(
            "BERRY",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로2",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300
        );
        // when & then
        assertThatCode(() -> liquorService.modifyLiquor(LIQUOR_ID, liquorModifyRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_LIQUOR_FOUND.getMessage());
    }
}
