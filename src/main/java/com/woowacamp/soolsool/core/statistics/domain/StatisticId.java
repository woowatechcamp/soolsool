package com.woowacamp.soolsool.core.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
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
}
