package com.woowacamp.soolsool.core.statistics.repository;

import com.woowacamp.soolsool.core.statistics.domain.Statistic;
import com.woowacamp.soolsool.core.statistics.infra.StatisticJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/statistics.sql"})
@DisplayName("단위 테스트: StatisticJpaRepository")
class StatisticJpaRepositoryTest {

    @Autowired
    StatisticJpaRepository statisticJpaRepository;

    @Test
    @DisplayName("단위 테스트 : 시작일과 종료일을 포함한 통계 데이터를 구한다.")
    void findAllByDate() {
        // given
        LocalDateTime startDate = LocalDateTime.of(2023, 9, 22, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 9, 22, 0, 0);

        // when
        List<Statistic> statistics = statisticJpaRepository.findAllByDateBetween(startDate, endDate);

        // then
        assertThat(statistics).size().isEqualTo(5);
    }
}
