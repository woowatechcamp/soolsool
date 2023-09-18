package com.woowacamp.soolsool.acceptance.fixture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.core.liquor.dto.liquorCtr.LiquorClickAddRequest;
import com.woowacamp.soolsool.core.liquor.dto.liquorCtr.LiquorImpressionAddRequest;
import io.restassured.RestAssured;
import java.util.List;

public abstract class RestLiquorCtrFixture extends RestFixture {

    public static void 술_노출수_증가(List<Long> liquorIds) {
        LiquorImpressionAddRequest request = new LiquorImpressionAddRequest(liquorIds);

        RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .body(request)
            .when().patch("/api/liquor-ctr/impressions")
            .then().log().all()
            .extract();
    }

    public static void 술_클릭수_증가(Long liquorId) {
        LiquorClickAddRequest request = new LiquorClickAddRequest(liquorId);

        RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .body(request)
            .when().patch("/api/liquor-ctr/clicks")
            .then().log().all()
            .extract();
    }
}
