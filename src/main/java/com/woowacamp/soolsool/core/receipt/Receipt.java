package com.woowacamp.soolsool.core.receipt;

import com.woowacamp.soolsool.core.receipt.converter.ReceiptPriceConverter;
import com.woowacamp.soolsool.core.receipt.converter.ReceiptQuantityConverter;
import com.woowacamp.soolsool.core.receipt.vo.ReceiptPrice;
import com.woowacamp.soolsool.core.receipt.vo.ReceiptQuantity;
import com.woowacamp.soolsool.global.common.BaseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "receipts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receipt extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "total_price")
    @Convert(converter = ReceiptPriceConverter.class)
    private ReceiptPrice totalPrice;

    @Column(name = "total_quantity")
    @Convert(converter = ReceiptQuantityConverter.class)
    private ReceiptQuantity totalQuantity;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<ReceiptItem> receiptItems = new ArrayList<>();

    @Builder
    public Receipt(
        final Long memberId,
        final ReceiptPrice totalPrice,
        final ReceiptQuantity totalQuantity,
        final LocalDateTime expiredAt
    ) {
        this.memberId = memberId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.expiredAt = expiredAt;
    }
}
