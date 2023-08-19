package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.auth.dto.LoginRequest;
import com.woowacamp.soolsool.core.auth.dto.LoginResponse;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("로그인할 때 멤버가 일치할 시, jwt 토큰을 발급 한다.")
    void loginSuccessTest() {
        // given
        String email = "woowafriends@naver.com";
        String password = "woowa";
        LoginRequest loginRequest = new LoginRequest(email, password);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(loginRequest).log().all()
            .when().post("/auth/login")
            .then().log().all()
            .extract();
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(new TypeRef<ApiResponse<LoginResponse>>() {
            }).getData()
            .getAccessToken())
            .isNotNull();
    }


}
