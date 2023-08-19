package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberMileageChargeRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import io.restassured.RestAssured;
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
        MemberAddRequest memberAddRequest = new MemberAddRequest(
            "CUSTOMER",
            "test@email.com",
            "test_password",
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역");

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .when().post("/members")
            .then().log().all()
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
            "test@email.com",
            "test_password",
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역");

        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .when().post("/members")
            .then();

        // when
        String accessToken = "accessToken";
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
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
            "test@email.com",
            "test_password",
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역");

        RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .when().post("/members")
            .then();

        MemberModifyRequest modifyRequest = new MemberModifyRequest(
            "modify_password",
            "modify_name",
            "modify_address");

        // when
        String accessToken = "accessToken";
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
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
            "test@email.com",
            "test_password",
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역");

        RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .when().post("/members")
            .then();

        // when
        String accessToken = "accessToken";
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .when().delete("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("성공 : 회원 마일리지 충전 성공")
    void chargeMemberMileage() {
        // given
        MemberAddRequest memberAddRequest = new MemberAddRequest(
            "CUSTOMER",
            "test@email.com",
            "test_password",
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역");

        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .when().post("/members")
            .then();

        String amount = "10000";
        MemberMileageChargeRequest memberMileageChargeRequest = new MemberMileageChargeRequest(
            amount
        );

        // when
        String accessToken = "accessToken";
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .body(memberMileageChargeRequest)
            .when().patch("/members/mileage")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
