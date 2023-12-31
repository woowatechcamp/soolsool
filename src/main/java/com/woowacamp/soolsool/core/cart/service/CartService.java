package com.woowacamp.soolsool.core.cart.service;

import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_EQUALS_MEMBER;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_FOUND_CART_ITEM;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_FOUND_LIQUOR;

import com.woowacamp.soolsool.core.cart.domain.Cart;
import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemModifyRequest;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.dto.response.CartItemResponse;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final LiquorRepository liquorRepository;

    @Transactional
    public Long addCartItem(final Long memberId, final CartItemSaveRequest request) {
        final Liquor liquor = liquorRepository.findById(request.getLiquorId())
            .orElseThrow(() -> new SoolSoolException(NOT_FOUND_LIQUOR));

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

    @Transactional(readOnly = true)
    public List<CartItemResponse> cartItemList(final Long memberId) {
        final List<CartItem> cartItems = cartItemRepository
            .findAllByMemberIdOrderByCreatedAtDescWithLiquor(memberId);

        return cartItems.stream()
            .map(CartItemResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void modifyCartItemQuantity(
        final Long memberId,
        final Long cartItemId,
        final CartItemModifyRequest cartItemModifyRequest
    ) {
        final CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new SoolSoolException(NOT_FOUND_CART_ITEM));

        validateMemberId(memberId, cartItem.getMemberId());

        cartItem.updateQuantity(cartItemModifyRequest.getLiquorQuantity());
    }

    @Transactional
    public void removeCartItem(final Long memberId, final Long cartItemId) {
        final CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new SoolSoolException(NOT_FOUND_CART_ITEM));

        validateMemberId(memberId, cartItem.getMemberId());

        cartItemRepository.delete(cartItem);
    }

    private void validateMemberId(final Long memberId, final Long cartItemMemberId) {
        if (!Objects.equals(cartItemMemberId, memberId)) {
            throw new SoolSoolException(NOT_EQUALS_MEMBER);
        }
    }

    @Transactional
    public void removeCartItems(final Long memberId) {
        cartItemRepository.deleteAllByMemberId(memberId);
    }
}
