package com.woowacamp.soolsool.core.statistics.service;

import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.statistics.domain.StatisticsLiquors;
import com.woowacamp.soolsool.core.statistics.dto.response.LiquorSalePriceResponse;
import com.woowacamp.soolsool.core.statistics.dto.response.LiquorSaleQuantityResponse;
import com.woowacamp.soolsool.core.statistics.repository.StatisticsRepository;
import java.util.Comparator;
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
    private final LiquorRepository liquorRepository;

    @Transactional(readOnly = true)
    public List<LiquorSalePriceResponse> findTop5LiquorsBySalePrice() {
        StatisticsLiquors statisticsLiquors = statisticsRepository.findTop5LiquorIdAndSalePrice();

        return liquorRepository.findAllById(statisticsLiquors.getIds()).stream()
            .map(liquor ->
                LiquorSalePriceResponse.from(liquor, statisticsLiquors.getValue(liquor.getId())))
            .sorted(Comparator.comparingLong(LiquorSalePriceResponse::getTotalSalePrice).reversed())
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<LiquorSaleQuantityResponse> findTop5LiquorsBySaleQuantity() {
        StatisticsLiquors top5LiquorByQuantities = statisticsRepository.findTop5LiquorIdAndSaleQuantity();

        return liquorRepository.findAllById(top5LiquorByQuantities.getIds()).stream()
            .map(liquor -> LiquorSaleQuantityResponse
                .from(liquor, top5LiquorByQuantities.getValue(liquor.getId())))
            .sorted(Comparator
                .comparingLong(LiquorSaleQuantityResponse::getTotalSaleQuantity).reversed())
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateStatistics() {
        statisticsRepository.updateStatistics();
    }
}
