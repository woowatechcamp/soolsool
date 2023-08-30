package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.NOT_LIQUOR_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStocks;
import com.woowacamp.soolsool.core.liquor.dto.LiquorStockSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStockRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
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
        final Liquor liquor = liquorRepository.findById(request.getLiquorId())
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));
        liquor.increaseTotalStock(request.getStock());

        return liquorStockRepository.save(request.toEntity()).getId();
    }

    @Transactional
    public void decreaseLiquorStock(final Long liquorId, final int quantity) {
        final LiquorStocks liquorStocks = new LiquorStocks(
            liquorStockRepository.findAllByLiquorIdNotExpired(liquorId)
        );
        liquorStocks.decreaseStock(quantity);

        liquorStockRepository.deleteAllInBatch(liquorStocks.getOutOfStocks());
    }
}
