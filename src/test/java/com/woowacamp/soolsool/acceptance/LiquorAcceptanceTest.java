package com.woowacamp.soolsool.acceptance;

import static com.woowacamp.soolsool.global.common.LiquorResultCode.LIQUOR_CREATED;
import static com.woowacamp.soolsool.global.common.LiquorResultCode.LIQUOR_DELETED;
import static com.woowacamp.soolsool.global.common.LiquorResultCode.LIQUOR_UPDATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
        assertThat(response.body().as(new TypeRef<ApiResponse<Void>>() {
        }).getMessage()).isEqualTo(LIQUOR_CREATED.getMessage());
    }

    @Test
    @DisplayName("술 상품을 수정할 수 있다.")
    void modifyLiquorTest() {
        // given
        String accessToken = "1234";
        LiquorSaveRequest saveLiquorRequest = new LiquorSaveRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);

        ExtractableResponse<Response> saveLiquor = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .body(saveLiquorRequest)
            .when().post("/liquors")
            .then().log().all()
            .extract();

        Long liquorId = Long.parseLong(saveLiquor.header("Location").split("/")[2]);
        LiquorModifyRequest liquorModifyRequest = new LiquorModifyRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "안동소주",
            "3000",
            "브랜드",
            "soolsool.png",
            100,
            12.0,
            1
        );

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .body(liquorModifyRequest)
            .when().put("/liquors/{liquorId}", liquorId)
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.body().as(new TypeRef<ApiResponse<Void>>() {
        }).getMessage()).isEqualTo(LIQUOR_UPDATED.getMessage());
    }

    @Test
    @DisplayName("술 상품을 삭제할 수 있다.")
    void removeLiquorTest() {
        // given
        String accessToken = "1234";
        LiquorSaveRequest saveLiquorRequest = new LiquorSaveRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "새로",
            "3000",
            "브랜드",
            "/url",
            100, 12.0,
            300);

        ExtractableResponse<Response> saveLiquor = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .body(saveLiquorRequest)
            .when().post("/liquors")
            .then().log().all()
            .extract();
        Long liquorId = Long.parseLong(saveLiquor.header("Location").split("/")[2]);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .when().delete("/liquors/{liquorId}", liquorId)
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(new TypeRef<ApiResponse<Void>>() {
        }).getMessage()).isEqualTo(LIQUOR_DELETED.getMessage());
    }
}
