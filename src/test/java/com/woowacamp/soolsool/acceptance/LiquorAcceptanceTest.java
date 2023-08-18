package com.woowacamp.soolsool.acceptance;

import static com.woowacamp.soolsool.global.common.LiquorResultCode.LIQUOR_CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@DisplayName("술 상품 관련 기능")
class LiquorAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("술 상품을 등록할 수 있다")
    void saveLiquor() {
        // given
        String accessToken = "1234";
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .body(liquorSaveRequest)
            .when().post("/liquors")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        ApiResponse actual = response.body().as(ApiResponse.class);
        assertThat(actual.getMessage())
            .isEqualTo(LIQUOR_CREATED.getMessage());
    }
}
