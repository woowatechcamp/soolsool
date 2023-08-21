package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.global.auth.dto.LoginRequest;
import com.woowacamp.soolsool.global.auth.dto.LoginResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("주문서 : 인수 테스트")
class ReceiptAcceptanceTest extends AcceptanceTest {

    private static final String BEARER_LITERAL = "Bearer ";

    @Test
    @DisplayName("성공 : 주문서를 생성한다.")
    void createReceiptSuccess() {
        // given
        String customerAccessToken = getCustomerAccessToken();
        String vendorAccessToken = getVendorAccessToken();

        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU", "GYEONGSANGNAM_DO", "ON_SALE",
            "안동소주", "12000", "안동", "/soju.jpeg",
            120, 31.3, 300
        );
        String location = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, vendorAccessToken)
            .body(liquorSaveRequest)
            .when().post("/liquors")
            .then().log().all()
            .extract().header("Location");
        Long liquorId = RestAssured
            .given().log().all()
            .accept(APPLICATION_JSON_VALUE)
            .when().get(location)
            .then().log().all()
            .extract().jsonPath().getObject("data", LiquorDetailResponse.class).getId();

        // when
        CartItemSaveRequest cartItemSaveRequest = new CartItemSaveRequest(liquorId, 1);
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, customerAccessToken)
            .body(cartItemSaveRequest)
            .when().post("/cart-items")
            .then().log().all()
            .extract();

        final ExtractableResponse<Response> createReceiptResponse = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, customerAccessToken)
            .body(cartItemSaveRequest)
            .when().post("/receipts")
            .then().log().all()
            .extract();

        assertThat(createReceiptResponse.statusCode()).isEqualTo(OK.value());
    }


    private String getCustomerAccessToken() {
        LoginRequest loginRequest = new LoginRequest("woowafriends@naver.com",
            "woowa");

        return BEARER_LITERAL + RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(loginRequest).log().all()
            .when().post("/auth/login")
            .then().log().all()
            .extract().jsonPath().getObject("data", LoginResponse.class).getAccessToken();
    }

    private String getVendorAccessToken() {
        LoginRequest loginRequest = new LoginRequest("test@email.com",
            "test_password");

        return BEARER_LITERAL + RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(loginRequest).log().all()
            .when().post("/auth/login")
            .then().log().all()
            .extract().jsonPath().getObject("data", LoginResponse.class).getAccessToken();
    }
}
