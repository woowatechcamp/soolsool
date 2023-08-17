package com.woowacamp.soolsool.support;

import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class TestFixture {

    public static ExtractableResponse<Response> addMember() {
        MemberAddRequest memberAddRequest = new MemberAddRequest(
            "CUSTOMER",
            "test@email.com",
            "test_password",
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역"
        );

        return RestAssured.given()
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .post("/members")
            .then()
            .extract();
    }

}
