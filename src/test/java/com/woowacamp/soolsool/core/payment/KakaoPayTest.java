package com.woowacamp.soolsool.core.payment;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.acceptance.AcceptanceTest;
import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.service.PayService;
import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class KakaoPayTest extends AcceptanceTest {

    @Autowired
    private PayService payService;
    @Autowired
    private LiquorService liquorService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ReceiptService receiptService;

    @Test
    @Disabled
    @DisplayName("결제 준비를 요청한다.")
    void payReadySuccess() {
        Long memberId = 3L;
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "새로", "3000", "브랜드", "/url",
            12.0, 300);

        Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);

        CartItemSaveRequest cartItemSaveRequest = new CartItemSaveRequest(
            saveLiquorId,
            10
        );
        cartService.addCartItem(memberId, cartItemSaveRequest);
        Long receiptId = receiptService.addReceipt(memberId);
        final PayReadyResponse payReadyResponse = payService.payReady(memberId,
            new PayOrderRequest(receiptId));
        assertThat(payReadyResponse.getNextRedirectPcUrl()).isNotNull();
    }

    @Test
    @Disabled
    @DisplayName("결제 승인을 요청한다.")
    void payApproveSuccess() {
        //payService.payApprove(3L, "d184a7312a9d806cdfde", 3L);

    }
}
