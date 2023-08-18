package com.woowacamp.soolsool.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.acceptance.AcceptanceTest;
import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import com.woowacamp.soolsool.support.TestFixture;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("회원 : 인수 테스트")
class MemberControllerTest extends AcceptanceTest {

    @Test
    @DisplayName("성공 : 회원 등록")
    void createMember() {
        // given
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
            .memberRoleType("CUSTOMER")
            .email("test@email.com")
            .password("test_password")
            .name("최배달")
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
        TestFixture.addMember();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when().get("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        MemberFindResponse memberFindResponse = response.as(MemberFindResponse.class);
        assertThat(memberFindResponse.getName()).isEqualTo("최배달");
    }

    @Test
    @DisplayName("성공 : 회원 수정")
    void modifyMember() {
        // given
        TestFixture.addMember();
        MemberModifyRequest modifyRequest = MemberModifyRequest.builder()
            .password("modify_password")
            .name("modify_name")
            .address("modify_address")
            .build();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
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
        TestFixture.addMember();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when()
            .delete("/members")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
