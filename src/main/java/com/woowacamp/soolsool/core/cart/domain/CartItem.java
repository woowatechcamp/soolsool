package com.woowacamp.soolsool.core.cart.domain;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.global.common.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
public class CartItem extends BaseEntity {

    private Long id;
    private Long memberId;
    private Liquor liquor;
    private int quantity;

}
