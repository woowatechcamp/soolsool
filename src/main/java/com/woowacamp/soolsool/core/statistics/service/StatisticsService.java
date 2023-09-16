package com.woowacamp.soolsool.core.statistics.service;

import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquors;
import com.woowacamp.soolsool.core.statistics.dto.response.LiquorSalePriceResponse;
import com.woowacamp.soolsool.core.statistics.dto.response.LiquorSaleQuantityResponse;
import com.woowacamp.soolsool.core.statistics.repository.StatisticsRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Transactional(readOnly = true)
    public List<LiquorSalePriceResponse> findTop5LiquorsBySalePrice() {
        final StatisticsLiquors statisticsLiquors = statisticsRepository.findTop5LiquorIdAndSalePrice();

        return statisticsLiquors.getValues().stream()
            .map(LiquorSalePriceResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<LiquorSaleQuantityResponse> findTop5LiquorsBySaleQuantity() {
        final StatisticsLiquors statisticsLiquors = statisticsRepository.findTop5LiquorIdAndSaleQuantity();

        return statisticsLiquors.getValues().stream()
            .map(LiquorSaleQuantityResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateStatistics() {
        statisticsRepository.updateStatistics();
    }
}
