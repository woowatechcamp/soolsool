package com.woowacamp.soolsool.core.cart.controller;

import static com.woowacamp.soolsool.core.cart.code.CartItemResultCode.CART_ITEM_ADD_SUCCESS;
import static com.woowacamp.soolsool.core.cart.code.CartItemResultCode.CART_ITEM_MODIFY_QUANTITY_SUCCESS;

import com.woowacamp.soolsool.core.cart.dto.request.CartItemModifyRequest;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> saveCartItem(
        @LoginUser final Long memberId,
        @RequestBody final CartItemSaveRequest cartItemSaveRequest
    ) {
        final Long cartItemId = cartService.saveCartItem(memberId, cartItemSaveRequest);

        return ResponseEntity.ok(
            ApiResponse.of(CART_ITEM_ADD_SUCCESS, cartItemId));
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> modifyCartItemQuantity(
        @LoginUser final Long memberId, @PathVariable Long cartItemId,
        @RequestBody final CartItemModifyRequest cartItemModifyRequest
    ) {
        cartService.modifyCartItemQuantity(memberId, cartItemId, cartItemModifyRequest);
        return ResponseEntity.ok(ApiResponse.from(CART_ITEM_MODIFY_QUANTITY_SUCCESS));
    }

    @
}
