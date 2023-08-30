package com.woowacamp.soolsool.acceptance.fixture;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberDetailResponse;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public abstract class RestMemberFixture extends RestFixture {

    public static void 회원가입_김배달_구매자() {
        MemberAddRequest memberAddRequest = new MemberAddRequest(
            "구매자",
            "kim@email.com",
            "baedal",
            "김배달",
            "010-0000-0000",
            "10000",
            "잠실역");

        RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .when().post("/api/members")
            .then().log().all()
            .extract();
    }

    public static void 회원가입_최민족_판매자() {
        MemberAddRequest memberAddRequest = new MemberAddRequest(
            "판매자",
            "choi@email.com",
            "minjok",
            "최민족",
            "010-1111-1111",
            "0",
            "구로디지털단지역");

        RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .when().post("/api/members")
            .then().log().all()
            .extract();
    }

    public static MemberDetailResponse 회원_정보_조회(String token) {
        return RestAssured
            .given().log().all()
            .header(AUTHORIZATION, BEARER + token)
            .when().get("/api/members")
            .then().log().all()
            .extract().jsonPath().getObject("data", MemberDetailResponse.class);
    }
}
