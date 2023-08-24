package com.woowacamp.soolsool.acceptance.fixture;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import io.restassured.RestAssured;

public abstract class RestPayFixture {

    public static void 결제_준비(String accessToken, PayOrderRequest request) {
        RestAssured
            .given().log().all()
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/pay/ready")
            .then().extract();
    }
}
