package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(LiquorService.class)
@DisplayName("술 service 통합 테스트")
class LiquorServiceTest {

    @Autowired
    private LiquorRepository liquorRepository;

    @Autowired
    private LiquorService liquorService;

    @Test
    @DisplayName("liquor를 저장한다.")
    void saveLiquorTest() {
        // given
        LiquorSaveRequest saveLiquorRequest = new LiquorSaveRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "새로", "3000", "브랜드", "/url",
            100, 12.0, 300,
            LocalDateTime.now().plusYears(5L));
        // when & then
        assertThatCode(() -> liquorService.saveLiquor(saveLiquorRequest))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("liquor를 수정한다.")
    void modifyLiquorTestWithSuccess() {
        // given
        LiquorSaveRequest saveLiquorRequest = new LiquorSaveRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "새로", "3000", "브랜드", "/url",
            100, 12.0, 300,
            LocalDateTime.now().plusYears(5L));
        Long saveLiquorId = liquorService.saveLiquor(saveLiquorRequest);
        LiquorModifyRequest liquorModifyRequest = new LiquorModifyRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "안동소주", "3000", "브랜드", "soolsool.png",
            100, 12.0, 1,
            LocalDateTime.now().plusYears(10L)
        );
        // when
        liquorService.modifyLiquor(saveLiquorId, liquorModifyRequest);

        // then
        Liquor findLiquor = liquorRepository.findById(saveLiquorId)
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));
        assertThat(findLiquor.getName()).isEqualTo(liquorModifyRequest.getName());
    }

}
