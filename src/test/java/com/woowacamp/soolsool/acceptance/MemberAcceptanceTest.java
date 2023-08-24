package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.woowacamp.soolsool.acceptance.fixture.RestAuthFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestMemberFixture;
import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberMileageChargeRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("인수 테스트: /member")
class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("성공 : 회원 등록")
    void createMember() {
        // given
        MemberAddRequest memberAddRequest = new MemberAddRequest(
            "구매자",
            "kim@email.com",
            "baedal",
            "김배달",
            "010-0000-0000",
            "0",
            "잠실역");

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
        RestMemberFixture.회원가입_김배달_구매자();
        String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + 김배달_토큰)
            .when().get("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        MemberFindResponse memberFindResponse = response.jsonPath()
            .getObject("data", MemberFindResponse.class);
        assertThat(memberFindResponse.getName()).isEqualTo("김배달");
    }

    @Test
    @DisplayName("성공 : 회원 수정")
    void modifyMember() {
        // given
        RestMemberFixture.회원가입_김배달_구매자();
        String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();

        MemberModifyRequest modifyRequest = new MemberModifyRequest(
            "modify_password",
            "modify_name",
            "modify_address");

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + 김배달_토큰)
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
        RestMemberFixture.회원가입_김배달_구매자();
        String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + 김배달_토큰)
            .when().delete("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("성공 : 마일리지 5000원 충전")
    void chargeMileage() {
        // given
        RestMemberFixture.회원가입_김배달_구매자();
        String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();

        MemberMileageChargeRequest request = new MemberMileageChargeRequest("5000");

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, BEARER + 김배달_토큰)
            .body(request)
            .when()
            .patch("members/mileage")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
