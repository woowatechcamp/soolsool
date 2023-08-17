package com.woowacamp.soolsool.core.cart.domain;

import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // TODO: 컴파일 에러 방지용 임시 생성자
public class Cart {

    private final Long memberId;
    private final Set<CartItem> cartItems;
}
