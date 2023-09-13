package com.woowacamp.soolsool.core.statistics.domain.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.woowacamp.soolsool.core.statistics.repository.StatisticsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StatisticsServiceTest {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Test
    @DisplayName("통계를 조회하는 테스트")
    void getStatisticsTest() {
        assertThatCode(()-> statisticsRepository.findAll()).doesNotThrowAnyException();
    }
}
