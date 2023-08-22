package com.woowacamp.soolsool.unit.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacamp.soolsool.core.cart.domain.Cart;
import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("장바구니 : 도메인 테스트")
class CartTest {

    private Liquor soju;
    private Liquor beer;

    @BeforeEach
    void setUpLiquor() {
        LiquorBrew sojuBrew = new LiquorBrew(LiquorBrewType.SOJU);
        LiquorBrew etcBrew = new LiquorBrew(LiquorBrewType.ETC);
        LiquorRegion gyeongSangNamDoRegion = new LiquorRegion(LiquorRegionType.GYEONGSANGNAM_DO);
        LiquorStatus onSaleStatus = new LiquorStatus(LiquorStatusType.ON_SALE);
        LiquorStatus stoppedStatus = new LiquorStatus(LiquorStatusType.STOPPED);

        soju = new Liquor(
            1L, sojuBrew, gyeongSangNamDoRegion, onSaleStatus,
            "안동 소주", "12000", "안동", "/soju.jpg",
            120, 21.7, 400,
            LocalDateTime.now()
        );
        beer = new Liquor(
            2L, etcBrew, gyeongSangNamDoRegion, stoppedStatus,
            "맥주", "5000", "OB", "/beer.jpg",
            20, 5.7, 500,
            LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("장바구니를 생성한다.")
    void createCart() {
        // given
        List<CartItem> cartItems = List.of(
            new CartItem(1L, soju, 1),
            new CartItem(1L, beer, 1)
        );

        // when & then
        assertDoesNotThrow(() -> new Cart(1L, cartItems));
    }

    @Test
    @DisplayName("장바구니의 memberId와 CartItem의 memberId가 다르면 예외를 던진다.")
    void sameMember() {
        // given
        List<CartItem> cartItems = List.of(
            new CartItem(1L, soju, 1),
            new CartItem(2L, beer, 1)
        );

        // when & then
        assertThatThrownBy(() -> new Cart(1L, cartItems))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("다른 사용자의 장바구니 상품을 가지고 있습니다.");
    }

    @Test
    @DisplayName("새로운 장바구니 상품을 추가할 때 100개를 초과하면 예외를 던진다.")
    void exceedMaxSize() {
        // given
        List<CartItem> cartItems = new ArrayList<>();
        for (long id = 1; id <= 100; id++) {
            // 생성 시 중복 검사를 하지 않으므로 편의상 같은 상품 반복 삽입
            cartItems.add(new CartItem(1L, soju, 1));
        }

        Cart cart = new Cart(1L, cartItems);

        CartItem newCartItem = new CartItem(1L, beer, 1);

        // when & then
        assertThatThrownBy(() -> cart.addCartItem(newCartItem))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("장바구니가 가득 찼습니다.");
    }

    @Test
    @DisplayName("새로운 장바구니 상품을 추가할 때 기존에 존재하는 상품이라면 예외를 던진다.")
    void duplicate() {
        // given
        CartItem cartItem = new CartItem(1L, soju, 1);
        CartItem sameCartItem = new CartItem(1L, soju, 1);

        List<CartItem> cartItems = new ArrayList<>(List.of(cartItem));

        Cart cart = new Cart(1L, cartItems);

        // when & then
        assertThatThrownBy(() -> cart.addCartItem(sameCartItem))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("장바구니에 이미 존재하는 상품입니다.");
    }

    @Test
    @DisplayName("새로운 장바구니 상품을 추가할 때 판매중지된 상품이라면 예외를 던진다.")
    void stoppedLiquor() {
        // given
        Cart cart = new Cart(1L, List.of());

        Liquor stoppedLiquor = Liquor.builder()
            .brew(new LiquorBrew(LiquorBrewType.SOJU))
            .region(new LiquorRegion(LiquorRegionType.GYEONGSANGNAM_DO))
            .status(new LiquorStatus(LiquorStatusType.STOPPED))
            .name("안동 소주")
            .price("12000")
            .brand("안동")
            .imageUrl("/soju.jpg")
            .stock(120)
            .alcohol(21.7)
            .volume(400)
            .build();

        CartItem cartItem = new CartItem(1L, stoppedLiquor, 1);

        // when & then
        assertThatThrownBy(() -> cart.addCartItem(cartItem))
            .isExactlyInstanceOf(SoolSoolException.class)
            .hasMessage("판매가 중지된 상품은 추가할 수 없습니다.");
    }
}
