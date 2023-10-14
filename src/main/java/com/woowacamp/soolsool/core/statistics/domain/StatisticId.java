package com.woowacamp.soolsool.core.statistics.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StatisticId implements Serializable {

    @Column(name = "statistic_year")
    private int year;

    @Column(name = "statistic_month")
    private int month;

    @Column(name = "statistic_week")
    private int week;

    @Column(name = "statistic_day")
    private int day;

    @Column(name = "liquor_id")
    private Long liquorId;
}
