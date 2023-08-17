package com.woowacamp.soolsool.acceptance.helper;

import com.woowacamp.soolsool.core.liquor.dto.ModifyLiquorRequest;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RestAssuredHelper {

    public static class Liquor {

        public static ExtractableResponse<Response> saveLiquor(String accessToken,
            SaveLiquorRequest request) {
            return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .body(request)
                .when().post("/liquors")
                .then().log().all()
                .extract();
        }

        public static ExtractableResponse<Response> modifyLiquor(
            String accessToken,
            Long liquorId,
            ModifyLiquorRequest modifyLiquorRequest) {
            return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .body(modifyLiquorRequest)
                .when().put("/liquors/{liquorId}", liquorId)
                .then().log().all()
                .extract();
        }
    }
}
