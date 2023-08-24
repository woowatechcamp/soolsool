package com.woowacamp.soolsool.acceptance;

import static com.woowacamp.soolsool.core.receipt.code.ReceiptResultCode.RECEIPT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.acceptance.fixture.RestAuthFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestCartFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestLiquorFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestLiquorStockFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestMemberFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestReceiptFixture;
import com.woowacamp.soolsool.core.receipt.dto.response.ReceiptResponse;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("인수 테스트: receipt")
class ReceiptAcceptanceTest extends AcceptanceTest {

    private static final String BEARER = "Bearer ";

    String 김배달_토큰;

    @BeforeEach
    void setUpData() {
        RestMemberFixture.회원가입_김배달_구매자();
        RestMemberFixture.회원가입_최민족_판매자();

        String 최민족_토큰 = RestAuthFixture.로그인_최민족_판매자();
        Long 새로_Id = RestLiquorFixture.술_등록_새로_판매중(최민족_토큰);
        Long 얼음딸기주_Id = RestLiquorFixture.술_등록_과일주_전라북도_얼음딸기주_우영미_판매중(최민족_토큰);

        RestLiquorStockFixture.술_재고_등록(최민족_토큰, 새로_Id, 100);
        RestLiquorStockFixture.술_재고_등록(최민족_토큰, 얼음딸기주_Id, 200);

        김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        RestCartFixture.장바구니_상품_추가(김배달_토큰, 새로_Id, 1);
        RestCartFixture.장바구니_상품_추가(김배달_토큰, 얼음딸기주_Id, 2);
    }

    @Test
    @DisplayName("성공 : 주문서를 생성한다.")
    void createReceiptSuccess() {
        // given

        // when
        ExtractableResponse<Response> createReceiptResponse = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, BEARER + 김배달_토큰)
            .when().post("/receipts")
            .then().log().all()
            .extract();

        // then
        assertThat(createReceiptResponse.statusCode()).isEqualTo(CREATED.value());
    }

    @Test
    @DisplayName("성공: 주문서를 조회한다.")
    void findReceiptSuccess() {
        // given
        final Long 주문서_Id = RestReceiptFixture.주문서_생성(김배달_토큰);

        // when
        ExtractableResponse<Response> detailReceiptResponse = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, BEARER + 김배달_토큰)
            .when().get("/receipts/{receiptId}", 주문서_Id)
            .then().log().all()
            .extract();

        // then
        assertThat(detailReceiptResponse.statusCode()).isEqualTo(OK.value());

        assertThat(detailReceiptResponse.body().as(new TypeRef<ApiResponse<ReceiptResponse>>() {
            })
            .getMessage())
            .isEqualTo(RECEIPT_FOUND.getMessage());
    }
}
