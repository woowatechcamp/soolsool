package com.woowacamp.soolsool.core.liquor.domain;

import com.woowacamp.soolsool.core.liquor.code.LiquorStockErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class LiquorStocks {

    private final Long liquorId;
    private final List<LiquorStock> liquorStocks;

    public LiquorStocks(final List<LiquorStock> liquorStocks) {
        validateEmptyLiquor(liquorStocks);
        validateAllSameLiquorId(liquorStocks);

        this.liquorStocks = liquorStocks;
        this.liquorId = liquorStocks.get(0).getLiquorId();
    }

    private void validateEmptyLiquor(final List<LiquorStock> liquorStocks) {
        if (liquorStocks.isEmpty()) {
            throw new SoolSoolException(LiquorStockErrorCode.EMPTY_LIQUOR_STOCKS);
        }
    }

    private void validateAllSameLiquorId(final List<LiquorStock> liquorStocks) {
        final long count = liquorStocks.stream()
            .mapToLong(LiquorStock::getLiquorId)
            .distinct()
            .count();

        if (count > 1) {
            throw new SoolSoolException(LiquorStockErrorCode.INCLUDE_OTHER_LIQUOR);
        }
    }

    // TODO: 코드 가독성 향상
    public void decreaseStock(int quantity) {
        validateEnoughStocks(quantity);

        for (LiquorStock liquorStock : liquorStocks) {
            if (quantity == 0) {
                break;
            }
            int target = Math.min(liquorStock.getStock(), quantity);
            quantity -= target;
            liquorStock.decreaseStock(target);
        }
    }

    private void validateEnoughStocks(final int quantity) {
        final int totalStock = liquorStocks.stream()
            .mapToInt(LiquorStock::getStock)
            .sum();
        
        if (totalStock < quantity) {
            throw new SoolSoolException(LiquorStockErrorCode.NOT_ENOUGH_LIQUOR_STOCKS);
        }
    }

    public List<LiquorStock> getOutOfStocks() {
        return this.liquorStocks.stream()
            .filter(LiquorStock::isOutOfStock)
            .collect(Collectors.toList());
    }
}
