package com.woowacamp.soolsool.core.statistics.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsLiquors {

    private final Map<Long, Long> liquorIdAndValue;

    public static StatisticsLiquors from(final List<StatisticsLiquor> statisticsLiquors) {
        return new StatisticsLiquors(
            statisticsLiquors.stream()
                .collect(Collectors.toUnmodifiableMap(
                    StatisticsLiquor::getLiquorId,
                    StatisticsLiquor::getLiquorValue)
                ));
    }

    public Set<Long> getIds() {
        return liquorIdAndValue.keySet();
    }

    public Long getValue(final Long key) {
        return liquorIdAndValue.get(key);
    }
}
