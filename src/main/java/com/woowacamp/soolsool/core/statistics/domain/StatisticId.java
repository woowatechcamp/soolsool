package com.woowacamp.soolsool.core.statistics.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class StatisticId implements Serializable {

    @Column(name = "statistics_year")
    private int year;

    @Column(name = "statistics_month")
    private int month;

    @Column(name = "statistics_week")
    private int week;

    @Column(name = "statistics_day")
    private int day;

    @Column(name = "liquor_id")
    private Long liquorId;

    @Builder
    public StatisticId(final int year, final int month, final int week, final int day, final Long liquorId) {
        this.year = year;
        this.month = month;
        this.week = week;
        this.day = day;
        this.liquorId = liquorId;
    }

    public static StatisticId from(final LocalDateTime localDateTime, final Long liquorId) {
        return StatisticId.builder()
                .year(localDateTime.getYear())
                .month(localDateTime.getMonthValue())
                .day(localDateTime.getDayOfMonth())
                .week(localDateTime.getDayOfWeek().getValue())
                .liquorId(liquorId)
                .build();
    }
}
