package com.woowacamp.soolsool.core.liquor.domain;

import static com.woowacamp.soolsool.core.liquor.code.LiquorErrorCode.INVALID_SIZE_STOCK;

import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorStockConverter;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStockVo;
import com.woowacamp.soolsool.core.liquor.domain.vo.Stock;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "liquor_stocks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquorStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id", nullable = false)
    @Getter
    private Liquor liquor;

    @Column(name = "stock", nullable = false)
    @Convert(converter = LiquorStockConverter.class)
    private LiquorStockVo stock;

    @Column(name = "expired_at", nullable = false)
    @Getter
    private LocalDateTime expiredAt;

    @Builder
    public LiquorStock(final Liquor liquor, final LiquorStockVo stock, final LocalDateTime expiredAt) {
        this.liquor = liquor;
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
        this.stock = new LiquorStockVo(this.stock.getStock() - quantity);
    }
}
