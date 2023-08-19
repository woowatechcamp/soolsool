package com.woowacamp.soolsool.core.cart.controller;

import com.woowacamp.soolsool.core.cart.code.CartItemResultCode;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.service.CartItemService;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveCartItem(
        @RequestBody final CartItemSaveRequest cartItemSaveRequest
    ) {
        // TODO: 로그인 구현 이후 ModelAttribute에서 memberId 가져오기
        final Long memberId = 1L;

        cartItemService.saveCartItem(memberId, cartItemSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.of(CartItemResultCode.CART_ITEM_ADD_SUCCESS, null));
    }
}
