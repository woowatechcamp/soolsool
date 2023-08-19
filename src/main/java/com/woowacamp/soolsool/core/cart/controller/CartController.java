package com.woowacamp.soolsool.core.cart.controller;

import com.woowacamp.soolsool.core.cart.code.CartItemResultCode;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<Void>> saveCartItem(
        @LoginUser final Long memberId,
        @RequestBody final CartItemSaveRequest cartItemSaveRequest
    ) {
        cartService.saveCartItem(memberId, cartItemSaveRequest);

        return ResponseEntity.ok(ApiResponse.from(CartItemResultCode.CART_ITEM_ADD_SUCCESS));
    }
}
