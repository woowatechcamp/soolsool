package com.woowacamp.soolsool.acceptance.fixture;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.dto.response.PaySuccessResponse;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;

public abstract class RestPayFixture {

    private static final String BEARER = "Bearer ";

    public static void 결제_준비(String accessToken, Long receiptId) {
        PayOrderRequest request = new PayOrderRequest(receiptId);

        RestAssured
            .given().log().all()
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/pay/ready")
            .then().extract().body().as(new TypeRef<ApiResponse<PayReadyResponse>>() {
            });
    }

    public static Long 결제_성공(String accessToken, Long receiptId) {
        return RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .param("pg_token", "pgpgpgpg")
            .when().get("/pay/success/{receiptId}", receiptId)
            .then().extract().body().as(new TypeRef<ApiResponse<PaySuccessResponse>>() {
            }).getData().getOrderId();
    }
}
