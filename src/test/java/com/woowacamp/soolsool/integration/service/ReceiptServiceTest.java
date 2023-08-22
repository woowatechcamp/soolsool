package com.woowacamp.soolsool.integration.service;

import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_FOUND_CART_ITEM;
import static com.woowacamp.soolsool.core.member.code.MemberErrorCode.MEMBER_NO_INFORMATION;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_EQUALS_MEMBER;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_RECEIPT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.core.receipt.dto.ReceiptResponse;
import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({ReceiptService.class, CartService.class, LiquorService.class})
@DisplayName("주문서 service 통합 테스트")
class ReceiptServiceTest {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private CartService cartService;
    @Autowired
    private LiquorService liquorService;

    @Test
    @DisplayName("주문서를 올바르게 생성한다.")
    void addReceiptTest() {
        // given
        Long memberId = 3L;
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);
        Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);
        CartItemSaveRequest cartItemSaveRequest = new CartItemSaveRequest(
            saveLiquorId,
            10
        );
        cartService.addCartItem(memberId, cartItemSaveRequest);

        // when & then
        assertThatCode(() -> receiptService.addReceipt(memberId))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("멤버가 존재하고, 카트에 주문할 게 없으면, 주문서가 올바르게 생성되지 않는다.")
    void addReceiptWithNoCartItems() {
        // given
        Long memberId = 4L;

        // when & then
        assertThatCode(() -> receiptService.addReceipt(memberId))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_FOUND_CART_ITEM.getMessage());
    }

    @Test
    @DisplayName("멤버가 존재하지 않으면, 주문서가 올바르게 생성되지 않는다.")
    void addReceiptWithNoMemberId() {
        // given
        Long memberId = 999L;
        // when & then
        assertThatCode(() -> receiptService.addReceipt(memberId))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(MEMBER_NO_INFORMATION.getMessage());
    }

    @Test
    @DisplayName("주문서를 올바르게 조회한다.")
    void receiptDetails() {
        Long memberId = 3L;
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);
        Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);
        CartItemSaveRequest cartItemSaveRequest = new CartItemSaveRequest(
            saveLiquorId,
            10
        );
        cartService.addCartItem(memberId, cartItemSaveRequest);
        Long receiptId = receiptService.addReceipt(memberId);

        // when
        ReceiptResponse receipt = receiptService.findReceipt(memberId, receiptId);
        // then
        assertThat(receipt).extracting("id").isEqualTo(receiptId);
    }

    @Test
    @DisplayName("멤버가 다르면, 주문서가 조회되지 않는다.")
    void receiptDetailWithNoMemberId() {
        // given
        Long memberId = 3L;
        Long anotherMemberId = 4L;
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);
        Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);
        CartItemSaveRequest cartItemSaveRequest = new CartItemSaveRequest(
            saveLiquorId,
            10
        );
        cartService.addCartItem(memberId, cartItemSaveRequest);
        Long receiptId = receiptService.addReceipt(memberId);

        // when & then
        assertThatCode(() -> receiptService.findReceipt(anotherMemberId, receiptId))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_EQUALS_MEMBER.getMessage());
    }

    @Test
    @DisplayName("주문서가 없으면 올바르게 조회되지 않는다.")
    void receiptDetailWithNoItems() {
        // given
        Long memberId = 4L;
        Long receiptId = 999L;

        // when & then
        assertThatCode(() -> receiptService.findReceipt(memberId, receiptId))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_RECEIPT_FOUND.getMessage());
    }
}