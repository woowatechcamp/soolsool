package com.woowacamp.soolsool.core.liquor.dto;

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

    public LiquorStock toEntity() {
        return LiquorStock.builder()
            .liquor(liquorId)
            .stock(new LiquorStockCount(stock))
            .expiredAt(expiredAt)
            .build();
    }
}
