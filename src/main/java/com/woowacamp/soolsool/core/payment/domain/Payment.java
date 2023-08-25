package com.woowacamp.soolsool.core.payment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // TODO: enum, 원시값 포장 등
    private String paymentMethodType; // 결제 수단 ( 현금 or 카드 )
    private String purchaseCorp; // 매입 카드사 한글명 (우리카드, 비씨카드)
    private String bin; // 카드 BIN
    private String installMonth; // 할부 개월 수
    private String approvedId; // 카드사 승인번호
    private String cardMid; // 카드사 가맹점번호

    @Builder
    public Payment(
        @NonNull final String paymentMethodType,
        final String purchaseCorp,
        final String bin,
        final String installMonth,
        final String approvedId,
        final String cardMid
    ) {
        this.paymentMethodType = paymentMethodType;
        this.purchaseCorp = purchaseCorp;
        this.bin = bin;
        this.installMonth = installMonth;
        this.approvedId = approvedId;
        this.cardMid = cardMid;
    }
}
