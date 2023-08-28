package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.acceptance.fixture.RestAuthFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestCartFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestLiquorFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestLiquorStockFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestMemberFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestPayFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestReceiptFixture;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import com.woowacamp.soolsool.core.order.dto.response.OrderDetailResponse;
import com.woowacamp.soolsool.core.order.dto.response.OrderListResponse;
import com.woowacamp.soolsool.core.order.dto.response.OrderRatioResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("인수 테스트: /orders")
class OrderAcceptanceTest extends AcceptanceTest {

    Long 새로, 얼음딸기주;

    @BeforeEach
    void setUpData() {
        RestMemberFixture.회원가입_김배달_구매자();
        RestMemberFixture.회원가입_최민족_판매자();

        String 최민족 = RestAuthFixture.로그인_최민족_판매자();
        새로 = RestLiquorFixture.술_등록_새로_판매중(최민족);
        얼음딸기주 = RestLiquorFixture.술_등록_과일주_전라북도_얼음딸기주_우영미_판매중(최민족);

        RestLiquorStockFixture.술_재고_등록(최민족, 새로, 100);
        RestLiquorStockFixture.술_재고_등록(최민족, 얼음딸기주, 200);
    }

    @Test
    @DisplayName("Order 상세내역을 조회한다.")
    void orderDetail() {
        // given
        String 김배달 = RestAuthFixture.로그인_김배달_구매자();
        RestCartFixture.장바구니_상품_추가(김배달, 새로, 1);
        RestCartFixture.장바구니_상품_추가(김배달, 얼음딸기주, 3);
        Long 김배달_주문서 = RestReceiptFixture.주문서_생성(김배달);
        RestPayFixture.결제_준비(김배달, 김배달_주문서);
        Long 김배달_주문 = RestPayFixture.결제_성공(김배달, 김배달_주문서);

        // when
        OrderDetailResponse response = RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + 김배달)
            .contentType(APPLICATION_JSON_VALUE)
            .when().get("/orders/" + 김배달_주문)
            .then().log().all()
            .extract().jsonPath().getObject("data", OrderDetailResponse.class);

        // then
        assertAll(
            () -> assertThat(response.getOrderId()).isEqualTo(김배달_주문),
            () -> assertThat(response.getOrderStatus())
                .isEqualTo(OrderStatusType.COMPLETED.getStatus()),
            () -> assertThat(response.getTotalQuantity()).isEqualTo(2),
            () -> assertThat(response.getPaymentInfo()).isNotNull()
        );
    }

    @Test
    @DisplayName("Order 리스트를 조회한다.")
    void orderList() {
        // given
        String 김배달 = RestAuthFixture.로그인_김배달_구매자();

        RestCartFixture.장바구니_상품_추가(김배달, 새로, 1);
        RestCartFixture.장바구니_상품_추가(김배달, 얼음딸기주, 3);
        Long 김배달_주문서 = RestReceiptFixture.주문서_생성(김배달);
        RestPayFixture.결제_준비(김배달, 김배달_주문서);
        RestPayFixture.결제_성공(김배달, 김배달_주문서);

        RestCartFixture.장바구니_상품_추가(김배달, 새로, 5);
        RestCartFixture.장바구니_상품_추가(김배달, 얼음딸기주, 2);
        Long 김배달_주문서2 = RestReceiptFixture.주문서_생성(김배달);
        RestPayFixture.결제_준비(김배달, 김배달_주문서2);
        RestPayFixture.결제_성공(김배달, 김배달_주문서2);

        // when
        List<OrderListResponse> data = RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + 김배달)
            .contentType(APPLICATION_JSON_VALUE)
            .when().get("/orders")
            .then().log().all()
            .extract().jsonPath().getList("data", OrderListResponse.class);

        // then
        assertThat(data).hasSize(2);
    }

    @Test
    @DisplayName("특정 술의 주문율을 조회한다.")
    void getOrderRatioByLiquorId() {
        // given
        String 김배달 = RestAuthFixture.로그인_김배달_구매자();

        RestCartFixture.장바구니_상품_추가(김배달, 새로, 1);
        Long 김배달_주문서 = RestReceiptFixture.주문서_생성(김배달);
        RestPayFixture.결제_준비(김배달, 김배달_주문서);
        RestPayFixture.결제_성공(김배달, 김배달_주문서);

        RestCartFixture.장바구니_상품_추가(김배달, 새로, 5);
        RestReceiptFixture.주문서_생성(김배달);

        /* when */
        OrderRatioResponse response = RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + 김배달)
            .contentType(APPLICATION_JSON_VALUE)
            .param("liquorId", 새로)
            .when().get("/orders/ratio")
            .then().log().all()
            .extract().jsonPath().getObject("data", OrderRatioResponse.class);

        /* then */
        assertThat(response.getRatio()).isEqualTo(50.0);
    }

    @Test
    @DisplayName("특정 주문을 취소한다.")
    void cancelOrder() throws Exception {
        /* given */
        String 김배달 = RestAuthFixture.로그인_김배달_구매자();

        RestCartFixture.장바구니_상품_추가(김배달, 새로, 1);
        Long 김배달_주문서 = RestReceiptFixture.주문서_생성(김배달);
        RestPayFixture.결제_준비(김배달, 김배달_주문서);
        final Long 주문번호 = RestPayFixture.결제_성공(김배달, 김배달_주문서);

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + 김배달)
            .contentType(APPLICATION_JSON_VALUE)
            .when().patch("/orders/cancel/{orderId}", 주문번호)
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(200);
    }
}
