package com.woowacamp.soolsool.core.payment.domain;

import javax.persistence.Column;
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
@Table(name = "kakao_pay_receipts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoPayReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "receipt_id", nullable = false)
    private Long receiptId;

    @Column(name = "tid", nullable = false)
    private String tid;

    @Builder
    public KakaoPayReceipt(final Long receiptId, final String tid) {
        this.receiptId = receiptId;
        this.tid = tid;
    }

    public static KakaoPayReceipt of(final Long receiptId, final String tid) {
        return KakaoPayReceipt.builder()
            .receiptId(receiptId)
            .tid(tid)
            .build();
    }
}
