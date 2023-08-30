package com.woowacamp.soolsool.core.order.domain;

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
import lombok.NonNull;

@Entity
@Table(name = "payment_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderPaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "payment_method_type")
    private String paymentMethodType; // 결제 수단 ( 현금 or 카드 )

    @Column(name = "purchase_corp")
    private String purchaseCorp; // 매입 카드사 한글명 (우리카드, 비씨카드)

    @Column(name = "bin")
    private String bin; // 카드 BIN

    @Column(name = "install_month")
    private String installMonth; // 할부 개월 수

    @Column(name = "approved_id")
    private String approvedId; // 카드사 승인번호

    @Column(name = "card_mid")
    private String cardMid; // 카드사 가맹점번호

    @Builder
    public OrderPaymentInfo(
        @NonNull final Long orderId,
        @NonNull final String paymentMethodType,
        final String purchaseCorp,
        final String bin,
        final String installMonth,
        final String approvedId,
        final String cardMid
    ) {
        this.orderId = orderId;
        this.paymentMethodType = paymentMethodType;
        this.purchaseCorp = purchaseCorp;
        this.bin = bin;
        this.installMonth = installMonth;
        this.approvedId = approvedId;
        this.cardMid = cardMid;
    }
}
