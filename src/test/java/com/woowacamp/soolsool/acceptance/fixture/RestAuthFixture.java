package com.woowacamp.soolsool.acceptance.fixture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.global.auth.dto.LoginRequest;
import com.woowacamp.soolsool.global.auth.dto.LoginResponse;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public abstract class RestAuthFixture {

    public static String 로그인_김배달_구매자() {
        LoginRequest loginRequest = new LoginRequest("kim@email.com", "baedal");

        ExtractableResponse<Response> loginResponse = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .body(loginRequest)
            .when().post("/api/auth/login")
            .then().log().all()
            .extract();

        return loginResponse.body()
            .as(new TypeRef<ApiResponse<LoginResponse>>() {
            })
            .getData()
            .getAccessToken();
    }

    public static String 로그인_최민족_판매자() {
        LoginRequest request = new LoginRequest("choi@email.com", "minjok");

        ExtractableResponse<Response> loginResponse = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/auth/login")
            .then().log().all()
            .extract();

        return loginResponse.body()
            .as(new TypeRef<ApiResponse<LoginResponse>>() {
            })
            .getData()
            .getAccessToken();
    }
}
