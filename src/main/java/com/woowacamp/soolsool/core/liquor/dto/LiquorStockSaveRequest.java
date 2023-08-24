package com.woowacamp.soolsool.core.liquor.dto;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStock;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStockCount;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LiquorStockSaveRequest {

    private final Long liquorId;
    private final Integer stock;
    private final LocalDateTime expiredAt;

    public LiquorStock toEntity(final Liquor liquor) {
        return LiquorStock.builder()
            .liquor(liquor)
            .stock(new LiquorStockCount(stock))
            .expiredAt(expiredAt)
            .build();
    }
}
