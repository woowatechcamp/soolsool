package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(LiquorService.class)
@DisplayName("술 service 통합 테스트")
class LiquorServiceIntegrationTest {

    @Autowired
    LiquorRepository liquorRepository;

    @Autowired
    LiquorRegionRepository liquorRegionRepository;

    @Autowired
    LiquorStatusRepository liquorStatusRepository;

    @Autowired
    LiquorBrewRepository liquorBrewRepository;

    @Autowired
    LiquorService liquorService;

    @Test
    @DisplayName("liquor를 저장한다.")
    void saveLiquorTest() {
        // given
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "새로", "3000", "브랜드", "/url",
            100, 12.0, 300,
            LocalDateTime.now().plusYears(5L));

        // when & then
        assertThatCode(() -> liquorService.saveLiquor(liquorSaveRequest))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("liquor를 수정한다.")
    void modifyLiquorTest() {
        // given
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "새로", "3000", "브랜드", "/url",
            100, 12.0, 300,
            LocalDateTime.now().plusYears(5L));
        Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);
        LiquorModifyRequest liquorModifyRequest = new LiquorModifyRequest(
            "BERRY", "GYEONGGI_DO", "ON_SALE",
            "새로2", "3000", "브랜드", "/url",
            100, 12.0, 300,
            LocalDateTime.now().plusYears(10L)
        );
        // when
        liquorService.modifyLiquor(saveLiquorId, liquorModifyRequest);

        // then
        Liquor liquor = liquorRepository.findById(saveLiquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));
        assertAll(
            () -> assertThat(liquor.getName()).isEqualTo(liquorModifyRequest.getName()),
            () -> assertThat(liquor.getName()).isNotEqualTo(liquorSaveRequest.getName())
        );
    }

    @Test
    @DisplayName("liquor Id가 존재하지 않을 때, 수정 시 에러를 반환한다.")
    void modifyLiquorTestFailWithNoExistId() {
        // given
        long LIQUOR_ID = 2L;
        LiquorModifyRequest liquorModifyRequest = new LiquorModifyRequest(
            "BERRY", "GYEONGGI_DO", "ON_SALE",
            "새로2", "3000", "브랜드", "/url",
            100, 12.0, 300,
            LocalDateTime.now().plusYears(10L)
        );
        // when & then
        assertThatCode(() -> liquorService.modifyLiquor(LIQUOR_ID, liquorModifyRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_LIQUOR_FOUND.getMessage());
    }


    @Test
    @DisplayName("liquor를 삭제한다.")
    void deleteLiquorTest() {
        // given
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "새로", "3000", "브랜드", "/url",
            100, 12.0, 300,
            LocalDateTime.now().plusYears(10L));
        Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);

        // when
        liquorService.deleteLiquor(saveLiquorId);
        Optional<Liquor> liquor = liquorRepository.findById(saveLiquorId);

        // then
        assertThat(liquor).isEmpty();
    }
}
