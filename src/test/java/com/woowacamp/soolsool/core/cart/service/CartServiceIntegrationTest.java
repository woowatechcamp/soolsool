package com.woowacamp.soolsool.core.cart.service;

import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.INVALID_QUANTITY_SIZE;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_EQUALS_MEMBER;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_FOUND_CART_ITEM;
import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_FOUND_LIQUOR;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacamp.soolsool.core.cart.dto.request.CartItemModifyRequest;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorQueryDslRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusCache;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.global.config.QuerydslConfig;
import com.woowacamp.soolsool.global.config.RedissonConfig;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import({CartService.class, LiquorService.class, LiquorBrewCache.class,
    LiquorStatusCache.class, LiquorRegionCache.class, LiquorQueryDslRepository.class,
    LiquorStatusCache.class, LiquorRegionCache.class,
    QuerydslConfig.class,
    RedissonConfig.class})
@DisplayName("통합 테스트: CartItemService")
class CartServiceIntegrationTest {

    @Autowired
    private CartService cartService;

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
    })
    @DisplayName("존재하지 않는 술을 장바구니 상품으로 추가할 경우 예외를 던진다.")
    void saveNotExistsLiquorByCartItem() {
        // given
        Long 김배달 = 1L;
        CartItemSaveRequest request = new CartItemSaveRequest(99999L, 1);

        // when & then
        assertThatThrownBy(() -> cartService.addCartItem(김배달, request))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_FOUND_LIQUOR.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql"
    })
    @DisplayName("장바구니 수량 변경으로 음수나 0이 들어올 경우 예외를 던진다.")
    void modifyCartItemQuantity(Integer quantity) {
        // given
        Long 김배달 = 1L;
        Long 김배달_장바구니_상품 = 1L;

        CartItemModifyRequest cartItemModifyRequest = new CartItemModifyRequest(quantity);

        // when & then
        assertThatCode(
            () -> cartService.modifyCartItemQuantity(
                김배달,
                김배달_장바구니_상품,
                cartItemModifyRequest))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(INVALID_QUANTITY_SIZE.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql"
    })
    @DisplayName("다른 사용자가 삭제하려고 할 시, 예외를 던진다.")
    void removeCartItemByNotEqualUser() {
        // given
        Long 최민족 = 2L;
        Long 김배달_장바구니_상품 = 1L;

        // when & then
        assertThatCode(() -> cartService.removeCartItem(최민족, 김배달_장바구니_상품))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_EQUALS_MEMBER.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql"
    })
    @DisplayName("장바구니에 없는 상품을 삭제할 시, 예외를 던진다.")
    void removeNoExistCartItem() {
        // given
        Long memberId = 1L;
        Long wrongCartItemId = 999L;

        // when & then
        assertThatCode(() -> cartService.removeCartItem(memberId, wrongCartItemId))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_FOUND_CART_ITEM.getMessage());
    }
}
