package com.woowacamp.soolsool.acceptance.fixture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import io.restassured.RestAssured;
import org.springframework.http.HttpHeaders;

public abstract class RestLiquorFixture extends RestFixture {

    public static Long 술_등록_새로_판매중(String accessToken) {
        LiquorSaveRequest request = new LiquorSaveRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "새로", "3000", "롯데", "/soju-url",
            12.0, 300
        );

        return addLiquor(accessToken, request);
    }

    public static Long 술_등록_ETC_경기도_하이트_진로_판매중지(String accessToken) {
        LiquorSaveRequest request = new LiquorSaveRequest(
            "ETC", "GYEONGGI_DO", "STOPPED",
            "하이트", "4000", "진로", "/beer-url",
            24.0, 600
        );

        return addLiquor(accessToken, request);
    }

    public static Long 술_등록_과일주_전라북도_얼음딸기주_우영미_판매중(String accessToken) {
        LiquorSaveRequest request = new LiquorSaveRequest(
            "BERRY", "JEOLLABUK_DO", "ON_SALE",
            "얼음딸기주", "4500", "우영미", "/strawberry-url",
            14.0, 400
        );

        return addLiquor(accessToken, request);
    }

    private static Long addLiquor(String accessToken, LiquorSaveRequest request) {
        return Long.valueOf(RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/liquors")
            .then().log().all()
            .extract().header("Location").split("/")[2]);
    }
}
