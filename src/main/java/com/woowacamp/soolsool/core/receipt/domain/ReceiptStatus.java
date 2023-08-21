package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.core.receipt.vo.ReceiptStatusType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "receipt_status")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 20)
    private ReceiptStatusType type;

    public ReceiptStatus(final ReceiptStatusType type) {
        this.type = type;
    }
}
