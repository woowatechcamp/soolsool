package com.woowacamp.soolsool.core.cart.controller;

import static com.woowacamp.soolsool.core.cart.code.CartResultCode.CART_ITEM_ADD_SUCCESS;
import static com.woowacamp.soolsool.core.cart.code.CartResultCode.CART_ITEM_DELETED;
import static com.woowacamp.soolsool.core.cart.code.CartResultCode.CART_ITEM_LIST_DELETED;
import static com.woowacamp.soolsool.core.cart.code.CartResultCode.CART_ITEM_LIST_FOUND;
import static com.woowacamp.soolsool.core.cart.code.CartResultCode.CART_ITEM_MODIFY_QUANTITY_SUCCESS;

import com.woowacamp.soolsool.core.cart.dto.request.CartItemModifyRequest;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.dto.response.CartItemResponse;
import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.common.ApiResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> addCartItem(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId,
        @RequestBody final CartItemSaveRequest cartItemSaveRequest
    ) {
        log.info("{} {} | memberId : {} | request : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(),
            memberId, cartItemSaveRequest);

        final Long cartItemId = cartService.addCartItem(memberId, cartItemSaveRequest);

        // TODO: DTO로 반환하기 "cartItemId": 1
        return ResponseEntity.ok(ApiResponse.of(CART_ITEM_ADD_SUCCESS, cartItemId));
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> modifyCartItemQuantity(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId,
        @PathVariable final Long cartItemId,
        @RequestBody final CartItemModifyRequest cartItemModifyRequest
    ) {
        log.info("{} {} | memberId : {} | request : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(),
            memberId, cartItemModifyRequest);

        cartService.modifyCartItemQuantity(memberId, cartItemId, cartItemModifyRequest);

        return ResponseEntity.ok(ApiResponse.from(CART_ITEM_MODIFY_QUANTITY_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> cartItemList(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId
    ) {
        log.info("{} {} | memberId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId);

        final List<CartItemResponse> cartItemResponses = cartService.cartItemList(memberId);

        return ResponseEntity.ok(ApiResponse.of(CART_ITEM_LIST_FOUND, cartItemResponses));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> removeCartItem(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId,
        @PathVariable final Long cartItemId
    ) {
        log.info("{} {} | memberId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId);

        cartService.removeCartItem(memberId, cartItemId);

        return ResponseEntity.ok(ApiResponse.from(CART_ITEM_DELETED));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> removeCartItemList(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId
    ) {
        log.info("{} {} | memberId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId);

        cartService.removeCartItems(memberId);

        return ResponseEntity.ok(ApiResponse.from(CART_ITEM_LIST_DELETED));
    }
}
