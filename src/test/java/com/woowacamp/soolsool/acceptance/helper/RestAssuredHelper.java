package com.woowacamp.soolsool.acceptance.helper;

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

        public static ExtractableResponse<Response> liquorDetail(String location) {
            return RestAssured
                    .given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when().get(location)
                    .then().log().all()
                    .extract();
        }

        public static ExtractableResponse<Response> liquorList() {
            return RestAssured
                    .given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/liquors")
                    .then().log().all()
                    .extract();
        }
    }
}
