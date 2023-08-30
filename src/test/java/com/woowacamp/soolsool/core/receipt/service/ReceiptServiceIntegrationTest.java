package com.woowacamp.soolsool.core.receipt.service;

import static com.woowacamp.soolsool.core.cart.code.CartErrorCode.NOT_FOUND_CART_ITEM;
import static com.woowacamp.soolsool.core.member.code.MemberErrorCode.MEMBER_NO_INFORMATION;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_EQUALS_MEMBER;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_RECEIPT_FOUND;
import static com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType.COMPLETED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorQueryDslRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionCache;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusCache;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.core.receipt.dto.response.ReceiptDetailResponse;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptStatusCache;
import com.woowacamp.soolsool.core.receipt.repository.redisson.ReceiptRedisRepository;
import com.woowacamp.soolsool.global.config.QuerydslConfig;
import com.woowacamp.soolsool.global.config.RedissonConfig;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import({ReceiptService.class, CartService.class, LiquorService.class,
    ReceiptMapper.class, LiquorBrewCache.class, LiquorStatusCache.class,
    LiquorRegionCache.class, ReceiptStatusCache.class,
    RedissonConfig.class, ReceiptRedisRepository.class,
    LiquorQueryDslRepository.class, QuerydslConfig.class})
@DisplayName("통합 테스트: ReceiptService")
class ReceiptServiceIntegrationTest {

    @Autowired
    private ReceiptService receiptService;

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql"
    })
    @DisplayName("주문서를 올바르게 생성한다.")
    void addReceiptTest() {
        // given
        Long 김배달 = 1L;

        // when & then
        assertThatCode(() -> receiptService.addReceipt(김배달))
            .doesNotThrowAnyException();
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql"
    })
    @DisplayName("회원가 존재하고, 카트에 주문할 게 없으면, 주문서를 생성할 수 없다.")
    void addReceiptWithNoCartItems() {
        // given
        Long 최민족 = 2L;

        // when & then
        assertThatCode(() -> receiptService.addReceipt(최민족))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_FOUND_CART_ITEM.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql"
    })
    @DisplayName("회원이 존재하지 않으면, 주문서를 생성할 수 없다.")
    void addReceiptWithNoMemberId() {
        // given
        Long memberId = 999L;

        // when & then
        assertThatCode(() -> receiptService.addReceipt(memberId))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(MEMBER_NO_INFORMATION.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql"
    })
    @DisplayName("주문서를 올바르게 조회한다.")
    void receiptDetails() {
        // given
        Long 김배달 = 1L;
        Long 김배달_주문서 = 1L;

        // when
        ReceiptDetailResponse receipt = receiptService.findReceipt(김배달, 김배달_주문서);
        // then
        assertThat(receipt).extracting("id").isEqualTo(김배달_주문서);
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql"
    })
    @DisplayName("회원가 다르면, 주문서가 조회되지 않는다.")
    void receiptDetailWithNoMemberId() {
        // given
        Long 최민족 = 2L;
        Long 김배달_주문서 = 1L;

        // when & then
        assertThatCode(() -> receiptService.findReceipt(최민족, 김배달_주문서))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_EQUALS_MEMBER.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql"
    })
    @DisplayName("주문서가 없으면 올바르게 조회되지 않는다.")
    void receiptDetailWithNoItems() {
        // given
        Long 김배달 = 1L;
        Long 존재하지_않는_주문서 = 999L;

        // when & then
        assertThatCode(() -> receiptService.findReceipt(김배달, 존재하지_않는_주문서))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_RECEIPT_FOUND.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql"
    })
    @DisplayName("주문서가 정상적으로 수정된다.")
    void receiptModifySuccess() {
        // given
        Long 김배달 = 1L;
        Long 김배달_주문서 = 1L;

        // when
        receiptService.modifyReceiptStatus(김배달, 김배달_주문서, COMPLETED);

        // then
        ReceiptDetailResponse receipt = receiptService.findReceipt(김배달, 김배달_주문서);
        assertThat(receipt.getReceiptStatus()).isEqualTo("COMPLETED");
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql"
    })
    @DisplayName("주문서의 주인이 아니면, 수정을 할 수 없다. ")
    void receiptModifyFailWithNotEqualMember() {
        // given
        Long 최민족 = 2L;
        Long 김배달_주문서 = 1L;

        // when & then
        assertThatCode(
            () -> receiptService.modifyReceiptStatus(최민족, 김배달_주문서, COMPLETED))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_EQUALS_MEMBER.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql", "/receipt.sql"
    })
    @DisplayName("주문서가 없으면,주문서의 상태를 수정을 할 수 없다. ")
    void receiptModifyFailWithNotExistReceipt() {
        // given
        Long 김배달 = 1L;
        Long 존재하지_않는_주문서 = 999L;

        // when & then
        assertThatCode(
            () -> receiptService.modifyReceiptStatus(김배달, 존재하지_않는_주문서, COMPLETED))
            .isInstanceOf(SoolSoolException.class)
            .hasMessage(NOT_RECEIPT_FOUND.getMessage());
    }
}
