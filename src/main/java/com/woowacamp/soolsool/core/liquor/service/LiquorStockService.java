package com.woowacamp.soolsool.core.liquor.service;

import com.woowacamp.soolsool.core.liquor.domain.LiquorStock;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStocks;
import com.woowacamp.soolsool.core.liquor.dto.LiquorStockSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStockRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LiquorStockService {

    private final LiquorRepository liquorRepository;
    private final LiquorStockRepository liquorStockRepository;

    @Transactional
    public Long saveLiquorStock(final LiquorStockSaveRequest request) {
        return liquorStockRepository.save(request.toEntity()).getId();
    }

    @Transactional
    public void decreaseLiquorStock(final Long liquorId, int quantity) {
        final LiquorStocks liquorStocks = getLiquorStocks(liquorId);

        liquorStocks.decreaseStock(quantity);

        // TODO: 비동기로
        final List<LiquorStock> removed = liquorStocks.stream()
            .filter(LiquorStock::isOutOfStock)
            .collect(Collectors.toList());

        liquorStockRepository.deleteAllInBatch(removed);
    }

    private LiquorStocks getLiquorStocks(final Long liquorId) {
        return new LiquorStocks(liquorStockRepository
            .findAllByLiquorIdNotExpired(liquorId));
    }
}
