package com.woowacamp.soolsool.integration.service;


import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.cart.service.CartItemService;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayName("CartItemService 테스트")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CartItemService.class})
class CartItemServiceTest {

    @Autowired
    private CartItemService cartItemService;

    @MockBean
    private CartItemRepository cartItemRepository;

    @MockBean
    private LiquorRepository liquorRepository;

    @Test
    @DisplayName("장바구니 상품으로 존재하지 않는 술을 추가할 경우 예외를 던진다.")
    void saveNotExistsLiquorByCartItem() {
        // given
        Long memberId = 1L;
        CartItemSaveRequest request = new CartItemSaveRequest(99999L, 1);

        // when & then
        Assertions.assertThrows(SoolSoolException.class,
            () -> cartItemService.saveCartItem(memberId, request));
    }
}
