package com.woowacamp.soolsool.acceptance.fixture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.core.liquor.dto.request.LiquorStockSaveRequest;
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;

public abstract class RestLiquorStockFixture extends RestFixture {

    public static void 술_재고_등록(String accessToken, Long liquorId, int quantity) {
        LiquorStockSaveRequest request = new LiquorStockSaveRequest(
            liquorId, quantity, LocalDateTime.now().plusYears(1L)
        );

        RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .body(request)
            .when().put("/api/liquor-stocks")
            .then().log().all()
            .extract();
    }
}
