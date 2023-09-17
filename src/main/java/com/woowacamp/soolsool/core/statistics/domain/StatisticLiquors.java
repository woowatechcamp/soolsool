package com.woowacamp.soolsool.core.statistics.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatisticLiquors {

    private final List<StatisticLiquorImpl> values;
}
