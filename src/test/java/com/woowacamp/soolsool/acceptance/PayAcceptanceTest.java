package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.acceptance.fixture.RestAuthFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestCartFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestLiquorFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestLiquorStockFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestMemberFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestPayFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestReceiptFixture;
import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.dto.response.PaySuccessResponse;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("인수 테스트: /pay")
class PayAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUpData() {
        RestMemberFixture.회원가입_김배달_구매자();
        RestMemberFixture.회원가입_최민족_판매자();

        String 최민족_토큰 = RestAuthFixture.로그인_최민족_판매자();
        Long 새로 = RestLiquorFixture.술_등록_새로_판매중(최민족_토큰);
        Long 하이트 = RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(최민족_토큰);
        Long 얼음딸기주 = RestLiquorFixture.술_등록_과일주_전라북도_얼음딸기주_우영미_판매중(최민족_토큰);
        RestLiquorStockFixture.술_재고_등록(최민족_토큰, 새로, 100);
        RestLiquorStockFixture.술_재고_등록(최민족_토큰, 하이트, 200);
        RestLiquorStockFixture.술_재고_등록(최민족_토큰, 얼음딸기주, 300);

        String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        RestCartFixture.장바구니_상품_추가(김배달_토큰, 새로, 1);
        RestCartFixture.장바구니_상품_추가(김배달_토큰, 얼음딸기주, 3);
    }

    @Test
    @DisplayName("주문서로 결제를 준비한다.")
    void ready() {
        /* given */
        String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        Long 김배달_주문서 = RestReceiptFixture.주문서_생성(김배달_토큰);

        PayOrderRequest request = new PayOrderRequest(김배달_주문서);

        /* when */
        final PayReadyResponse data = RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + 김배달_토큰)
            .contentType(APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/pay/ready")
            .then().extract().body().as(new TypeRef<ApiResponse<PayReadyResponse>>() {
            })
            .getData();

        /* then */
        assertThat(data.getTid()).isEqualTo("1");
        assertThat(data.getNextRedirectPcUrl()).isEqualTo("http://지들끼리이야기하는URL");
        assertThat(data.getNextRedirectMobileUrl()).isEqualTo("http://지들끼리이야기하는URL");
        assertThat(data.getNextRedirectAppUrl()).isEqualTo("http://지들끼리이야기하는URL");
    }

    @Test
    @DisplayName("결제 성공을 알린다.")
    void success() {
        /* given */
        String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        Long 김배달_주문서 = RestReceiptFixture.주문서_생성(김배달_토큰);
        RestPayFixture.결제_준비(김배달_토큰, 김배달_주문서);

        /* when */
        PaySuccessResponse response = RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + 김배달_토큰)
            .contentType(APPLICATION_JSON_VALUE)
            .param("pg_token", "pgpgpgpg")
            .when().get("/pay/success/{receiptId}", 김배달_주문서)
            .then().extract().body().as(new TypeRef<ApiResponse<PaySuccessResponse>>() {
            }).getData();

        /* then */
        assertThat(response.getOrderId()).isNotNull();
    }
}
