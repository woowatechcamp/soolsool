package com.woowacamp.soolsool.core.statistics.domain;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatisticLiquors {

    private final List<StatisticLiquorImpl> values;
}
