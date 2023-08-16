package com.woowacamp.soolsool.core.cart.domain;

import static lombok.AccessLevel.PROTECTED;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "cart_items")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class CartItem extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id", nullable = false)
    private Liquor liquor;

    @ColumnDefault("1")
    @Column(name = "quantity", nullable = false)
    private CartItemQuantity quantity;

    @Builder
    public CartItem(
        final Long memberId,
        final Liquor liquor,
        final CartItemQuantity quantity
    ) {
        this.memberId = memberId;
        this.liquor = liquor;
        this.quantity = quantity;
    }
}
