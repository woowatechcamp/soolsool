package com.woowacamp.soolsool.core.cart.service;

import com.woowacamp.soolsool.core.cart.code.CartItemErrorCode;
import com.woowacamp.soolsool.core.cart.domain.Cart;
import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final LiquorRepository liquorRepository;

    @Transactional
    public void saveCartItem(final Long memberId, final CartItemSaveRequest request) {
        final Liquor liquor = liquorRepository.findById(request.getLiquorId())
            .orElseThrow(() -> new SoolSoolException(CartItemErrorCode.NOT_FOUND_LIQUOR));

        final CartItem newCartItem = CartItem.builder()
            .memberId(memberId)
            .liquor(liquor)
            .quantity(request.getQuantity())
            .build();

        final Cart cart = new Cart(memberId, findAllByMemberIdOrderByCreatedAtDesc(memberId));
        cart.addCartItem(newCartItem);

        cartItemRepository.save(newCartItem);
    }

    private List<CartItem> findAllByMemberIdOrderByCreatedAtDesc(final Long memberId) {
        return cartItemRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);
    }
}
