package com.woowacamp.soolsool.core.cart.domain;

import static lombok.AccessLevel.PROTECTED;

import com.woowacamp.soolsool.core.cart.domain.converter.CartItemQuantityConverter;
import com.woowacamp.soolsool.core.cart.domain.vo.CartItemQuantity;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.global.common.BaseEntity;
import com.woowacamp.soolsool.global.exception.GlobalErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.Objects;
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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "cart_items")
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
public class CartItem extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Column(name = "member_id", nullable = false)
    @Getter
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id", nullable = false)
    @Getter
    private Liquor liquor;

    @ColumnDefault("1")
    @Column(name = "quantity", nullable = false)
    @Convert(converter = CartItemQuantityConverter.class)
    private CartItemQuantity quantity;

    @Builder
    public CartItem(
        final Long memberId,
        final Liquor liquor,
        final int quantity
    ) {
        validateIsNotNullLiquor(liquor);

        this.memberId = memberId;
        this.liquor = liquor;
        this.quantity = new CartItemQuantity(quantity);
    }

    public int getQuantity() {
        return this.quantity.getQuantity();
    }

    private void validateIsNotNullLiquor(final Liquor liquor) {
        if (Objects.isNull(liquor)) {
            throw new SoolSoolException(GlobalErrorCode.NO_CONTENT);
        }
    }

    public boolean hasSameLiquorWith(final CartItem other) {
        if (liquor == null || other.liquor == null) {
            return false;
        }

        return liquor.equals(other.liquor);
    }

    public boolean hasDifferentMemberIdWith(final Long otherMemberId) {
        return !memberId.equals(otherMemberId);
    }

    public boolean hasStoppedLiquor() {
        return liquor.isStopped();
    }
}
