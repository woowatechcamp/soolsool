package com.woowacamp.soolsool.acceptance.fixture;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.core.receipt.dto.response.ReceiptDetailResponse;
import io.restassured.RestAssured;

public abstract class RestReceiptFixture extends RestFixture {

    public static Long 주문서_생성(String 토큰) {
        return Long.valueOf(RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, BEARER + 토큰)
            .when().post("/api/receipts")
            .then().log().all()
            .extract().header("Location").split("/")[2]);
    }

    public static ReceiptDetailResponse 주문서_조회(String 토큰, Long 주문서) {
        return RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, BEARER + 토큰)
            .when().get("/api/receipts/{receiptId}", 주문서)
            .then().log().all()
            .extract().jsonPath().getObject("data", ReceiptDetailResponse.class);
    }
}
