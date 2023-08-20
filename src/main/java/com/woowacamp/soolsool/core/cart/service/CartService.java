package com.woowacamp.soolsool.core.cart.service;

import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_EQUALS_MEMBER;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NULL_LIQUOR;

import com.woowacamp.soolsool.core.cart.code.CartErrorCode;
import com.woowacamp.soolsool.core.cart.domain.Cart;
import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemModifyRequest;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final LiquorRepository liquorRepository;

    @Transactional
    public Long saveCartItem(final Long memberId, final CartItemSaveRequest request) {
        final Liquor liquor = liquorRepository.findById(request.getLiquorId())
            .orElseThrow(() -> new SoolSoolException(CartErrorCode.NOT_FOUND_LIQUOR));

        CartItem newCartItem = CartItem.builder()
            .memberId(memberId)
            .liquor(liquor)
            .quantity(request.getQuantity())
            .build();

        final Cart cart = new Cart(memberId, findAllByMemberIdOrderByCreatedAtDesc(memberId));
        cart.addCartItem(newCartItem);

        return cartItemRepository.save(newCartItem).getId();
    }

    private List<CartItem> findAllByMemberIdOrderByCreatedAtDesc(final Long memberId) {
        return cartItemRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);
    }

    @Transactional
    public void modifyCartItemQuantity(
        final Long memberId,
        final Long cartItemId,
        final CartItemModifyRequest cartItemModifyRequest) {
        final CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new SoolSoolException(NULL_LIQUOR));

        if (!Objects.equals(cartItem.getMemberId(), memberId)) {
            throw new SoolSoolException(NOT_EQUALS_MEMBER);
        }
        cartItem.updateQuantity(cartItemModifyRequest.getLiquorQuantity());
    }


}
