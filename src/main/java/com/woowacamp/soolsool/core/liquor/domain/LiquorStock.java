package com.woowacamp.soolsool.core.liquor.domain;

import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.INVALID_SIZE_STOCK;

import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorStockCountConverter;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStockCount;
import com.woowacamp.soolsool.global.common.BaseEntity;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "liquor_stocks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquorStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Column(name = "liquor_id", nullable = false)
    private Long liquorId;

    @Column(name = "stock", nullable = false)
    @Convert(converter = LiquorStockCountConverter.class)
    private LiquorStockCount stock;

    @Column(name = "expired_at", nullable = false)
    @Getter
    private LocalDateTime expiredAt;

    @Builder
    public LiquorStock(
        final Long liquorId,
        final LiquorStockCount stock,
        final LocalDateTime expiredAt
    ) {
        this.liquorId = liquorId;
        this.stock = stock;
        this.expiredAt = expiredAt;
    }

    public int getStock() {
        return this.stock.getStock();
    }

    public void decreaseStock(final int quantity) {
        if (this.stock.getStock() < quantity) {
            throw new SoolSoolException(INVALID_SIZE_STOCK);
        }
        this.stock = new LiquorStockCount(this.stock.getStock() - quantity);
    }
}
