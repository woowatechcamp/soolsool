package com.woowacamp.soolsool.core.statistics.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: StatisticsLiquors")
class StatisticsLiquorsTest {

    private StatisticsLiquor 주류1;
    private StatisticsLiquor 주류2;
    private StatisticsLiquor 주류3;
    private StatisticsLiquor 주류4;
    private StatisticsLiquor 주류5;

    @BeforeEach
    void setUp() {
        주류1 = new StatisticsLiquor() {
            @Override
            public Long getLiquorId() {
                return 1L;
            }

            @Override
            public Long getLiquorValue() {
                return 10L;
            }
        };

        주류2 = new StatisticsLiquor() {
            @Override
            public Long getLiquorId() {
                return 2L;
            }

            @Override
            public Long getLiquorValue() {
                return 20L;
            }
        };

        주류3 = new StatisticsLiquor() {
            @Override
            public Long getLiquorId() {
                return 3L;
            }

            @Override
            public Long getLiquorValue() {
                return 30L;
            }
        };

        주류4 = new StatisticsLiquor() {
            @Override
            public Long getLiquorId() {
                return 4L;
            }

            @Override
            public Long getLiquorValue() {
                return 40L;
            }
        };

        주류5 = new StatisticsLiquor() {
            @Override
            public Long getLiquorId() {
                return 5L;
            }

            @Override
            public Long getLiquorValue() {
                return 50L;
            }
        };
    }

    @Test
    @DisplayName("객체 생성 성공")
    void create() {
        // given
        List<StatisticsLiquor> statisticsLiquors = List.of(주류1, 주류2, 주류3, 주류4, 주류5);

        // when & then
        assertThatNoException()
            .isThrownBy(() -> StatisticsLiquors.from(statisticsLiquors));
    }

    @Test
    @DisplayName("id 정상 반환")
    void getIds() {
        // given
        StatisticsLiquors statisticsLiquors = StatisticsLiquors
            .from(List.of(주류1, 주류2, 주류3, 주류4, 주류5));

        // when
        Set<Long> liquorIds = statisticsLiquors.getIds();

        // then
        assertThat(liquorIds).hasSize(5);
    }

    @Test
    @DisplayName("값 정상 반환")
    void getValue() {
        // given
        StatisticsLiquors statisticsLiquors = StatisticsLiquors
            .from(List.of(주류1, 주류2, 주류3, 주류4, 주류5));

        // when
        Long 주류1_값 = statisticsLiquors.getValue(주류1.getLiquorId());

        // then
        assertThat(주류1_값).isEqualTo(10L);
    }
}
