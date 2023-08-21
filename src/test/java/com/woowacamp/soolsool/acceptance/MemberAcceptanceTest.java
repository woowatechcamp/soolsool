package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import com.woowacamp.soolsool.global.auth.dto.LoginRequest;
import com.woowacamp.soolsool.global.auth.dto.LoginResponse;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("회원 : 인수 테스트")
class MemberAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "test2@email.com";
    private static final String PASSWORD = "test_password2";

    private String findToken(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        ExtractableResponse<Response> loginResponse = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(loginRequest).log().all()
            .when().post("/auth/login")
            .then().log().all()
            .extract();

        String accessToken = loginResponse.body().as(new TypeRef<ApiResponse<LoginResponse>>() {
            })
            .getData()
            .getAccessToken();
        return accessToken;
    }

    @Test
    @DisplayName("성공 : 회원 등록")
    void createMember() {
        // given
        MemberAddRequest memberAddRequest = new MemberAddRequest(
            "CUSTOMER",
            EMAIL,
            PASSWORD,
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역");

        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .log().all()
            .when()
            .post("/members")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("성공 : 회원 조회")
    void getMember() {
        // given
        MemberAddRequest memberAddRequest = new MemberAddRequest(
            "CUSTOMER",
            EMAIL,
            PASSWORD,
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역");

        RestAssured.given()
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .post("/members")
            .then();
        String token = findToken(EMAIL, PASSWORD);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .when().get("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        MemberFindResponse memberFindResponse = response
            .jsonPath()
            .getObject("data", MemberFindResponse.class);
        assertThat(memberFindResponse.getName()).isEqualTo("최배달");
    }

    @Test
    @DisplayName("성공 : 회원 수정")
    void modifyMember() {
        // given
        MemberAddRequest memberAddRequest = new MemberAddRequest(
            "CUSTOMER",
            EMAIL,
            PASSWORD,
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역");

        RestAssured.given()
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .post("/members")
            .then();
        String token = findToken(EMAIL, PASSWORD);

        MemberModifyRequest modifyRequest = new MemberModifyRequest(
            "modify_password",
            "modify_name",
            "modify_address");

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(modifyRequest)
            .when().patch("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("성공 : 회원 삭제")
    void deleteMember() {
        // given
        MemberAddRequest memberAddRequest = new MemberAddRequest(
            "CUSTOMER",
            EMAIL,
            PASSWORD,
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역");

        RestAssured.given()
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .post("/members")
            .then();
        String token = findToken(EMAIL, PASSWORD);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .when().delete("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
