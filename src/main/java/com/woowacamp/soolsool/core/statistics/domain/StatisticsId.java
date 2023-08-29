package com.woowacamp.soolsool.core.statistics.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StatisticsId implements Serializable {

    @Column(name = "year")
    private int year;

    @Column(name = "month")
    private int month;

    @Column(name = "week")
    private int week;

    @Column(name = "day")
    private int day;

    @Column(name = "liquor_id")
    private Long liquorId;
}
