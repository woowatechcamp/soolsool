package com.woowacamp.soolsool.core.receipt;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.receipt.converter.ReceiptQuantityConverter;
import com.woowacamp.soolsool.core.receipt.vo.ReceiptQuantity;
import com.woowacamp.soolsool.global.common.BaseEntity;
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
@Table(name = "receipt_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id", nullable = false)
    private Liquor liquor;

    @Column(name = "quantity", nullable = false)
    @Convert(converter = ReceiptQuantityConverter.class)
    private ReceiptQuantity quantity;

    @Builder
    public ReceiptItem(final Liquor liquor, final ReceiptQuantity quantity) {
        this.liquor = liquor;
        this.quantity = quantity;
    }
}
