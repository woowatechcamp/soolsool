package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.member.controller.LoginResponse;
import com.woowacamp.soolsool.core.member.dto.request.LoginRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
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

    @Test
    @DisplayName("성공 : 회원 등록")
    void createMember() {
        // given
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
            .memberRoleType("CUSTOMER")
            .email("woowafriendss@naver.com")
            .password("woowa")
            .name("김배달")
            .phoneNumber("010-1234-5678")
            .mileage("0")
            .address("서울시 잠실역")
            .build();

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
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
            .memberRoleType("CUSTOMER")
            .email("woowafriends@naver.com")
            .password("woowa")
            .name("김배달")
            .phoneNumber("010-1234-5678")
            .mileage("0")
            .address("서울시 잠실역")
            .build();

        final String token = findToken(memberAddRequest);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .when().get("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        MemberFindResponse memberFindResponse = response.as(MemberFindResponse.class);
        assertThat(memberFindResponse.getName()).isEqualTo("김배달");
    }

    @Test
    @DisplayName("성공 : 회원 수정")
    void modifyMember() {
        // given
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
            .memberRoleType("CUSTOMER")
            .email("woowafriends@naver.com")
            .password("woowa")
            .name("최배달")
            .phoneNumber("010-1234-5678")
            .mileage("0")
            .address("서울시 잠실역")
            .build();

        final String token = findToken(memberAddRequest);
        MemberModifyRequest modifyRequest = MemberModifyRequest.builder()
            .password("modify_password")
            .name("modify_name")
            .address("modify_address")
            .build();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .body(modifyRequest)
            .when().patch("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private String findToken(MemberAddRequest memberAddRequest) {
        RestAssured.given()
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .post("/members")
            .then();

        String email = "woowafriends@naver.com";
        String password = "woowa";
        LoginRequest loginRequest = new LoginRequest(email, password);
        // when
        ExtractableResponse<Response> loginResponse = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(loginRequest).log().all()
            .when().post("/auth/login")
            .then().log().all()
            .extract();
        final String accessToken = loginResponse.body()
            .as(new TypeRef<ApiResponse<LoginResponse>>() {
            }).getData().getAccessToken();
        return accessToken;
    }

    @Test
    @DisplayName("성공 : 회원 삭제")
    void deleteMember() {
        // given
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
            .memberRoleType("CUSTOMER")
            .email("woowafriends@naver.com")
            .password("woowa")
            .name("최배달")
            .phoneNumber("010-1234-5678")
            .mileage("0")
            .address("서울시 잠실역")
            .build();
        final String accessToken = findToken(memberAddRequest);
        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when()
            .delete("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
