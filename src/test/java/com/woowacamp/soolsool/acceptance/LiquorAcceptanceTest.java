package com.woowacamp.soolsool.acceptance;

import static com.woowacamp.soolsool.global.common.LiquorResultCode.LIQUOR_CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.CREATED;

import com.woowacamp.soolsool.acceptance.helper.RestAssuredHelper.Liquor;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@DisplayName("술 상품 관련 기능")
class LiquorAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("술 상품을 등록할 수 있다")
    void saveLiquor() {
        // given
        String accessToken = "1234";
        SaveLiquorRequest saveLiquorRequest = new SaveLiquorRequest(
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
        ExtractableResponse<Response> response = Liquor.saveLiquor(
                accessToken,
                saveLiquorRequest
        );

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        ApiResponse actual = response.body().as(ApiResponse.class);
        assertThat(actual.getMessage())
                .isEqualTo(LIQUOR_CREATED.getMessage());
    }

    @Test
    @DisplayName("술 상세정보를 조회할 수 있다")
    void liquorDetail() {
        // given
        SaveLiquorRequest saveLiquorRequest = new SaveLiquorRequest(
                "SOJU", "GYEONGGI_DO", "ON_SALE",
                "새로", "3000", "브랜드", "/url",
                100, 12.0, 300);

        String location = Liquor.saveLiquor("accessToken", saveLiquorRequest).header("Location");

        // when
        LiquorDetailResponse liquorDetailResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(location)
                .then().log().all()
                .extract().jsonPath().getObject("data", LiquorDetailResponse.class);

        // then
        assertAll(
                () -> assertThat(liquorDetailResponse.getName()).isEqualTo(saveLiquorRequest.getName()),
                () -> assertThat(liquorDetailResponse.getPrice()).isEqualTo(saveLiquorRequest.getPrice()),
                () -> assertThat(liquorDetailResponse.getBrand()).isEqualTo(saveLiquorRequest.getBrand()),
                () -> assertThat(liquorDetailResponse.getImageUrl()).isEqualTo(saveLiquorRequest.getImageUrl()),
                () -> assertThat(liquorDetailResponse.getStock()).isEqualTo(saveLiquorRequest.getStock()),
                () -> assertThat(liquorDetailResponse.getAlcohol()).isEqualTo(saveLiquorRequest.getAlcohol()),
                () -> assertThat(liquorDetailResponse.getVolume()).isEqualTo(saveLiquorRequest.getVolume())
        );
    }

    @Test
    @DisplayName("술 목록을 최신순으로 조회할 수 있다")
    void liquorList() {
        // given
        SaveLiquorRequest saveSojuRequest = new SaveLiquorRequest(
                "SOJU", "GYEONGGI_DO", "ON_SALE",
                "새로", "3000", "브랜드", "/soju-url",
                100, 12.0, 300);
        SaveLiquorRequest saveBeerRequest = new SaveLiquorRequest(
                "ETC", "GYEONGGI_DO", "ON_SALE",
                "하이트", "4000", "진로", "/beer-url",
                200, 24.0, 600);
        String accessToken = "accessToken";

        Liquor.saveLiquor(accessToken, saveSojuRequest);
        Liquor.saveLiquor(accessToken, saveBeerRequest);

        // when
        List<LiquorElementResponse> liquors = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/liquors")
                .then().log().all()
                .extract().jsonPath().getList("data", LiquorElementResponse.class);

        // then
        assertAll(
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getName))
                        .containsExactly("하이트", "새로"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getPrice))
                        .containsExactly("4000", "3000"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getImageUrl))
                        .containsExactly("/beer-url", "/soju-url"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getStock))
                        .containsExactly(200, 100)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류로 검색할 수 있다")
    void liquorListByLiquorType() {
        // given
        SaveLiquorRequest saveSojuRequest = new SaveLiquorRequest(
                "SOJU", "GYEONGGI_DO", "ON_SALE",
                "새로", "3000", "브랜드", "/soju-url",
                100, 12.0, 300);
        SaveLiquorRequest saveBeerRequest = new SaveLiquorRequest(
                "ETC", "GYEONGGI_DO", "ON_SALE",
                "하이트", "4000", "진로", "/beer-url",
                200, 24.0, 600);
        String accessToken = "accessToken";

        Liquor.saveLiquor(accessToken, saveSojuRequest);
        Liquor.saveLiquor(accessToken, saveBeerRequest);

        // when
        List<LiquorElementResponse> liquors = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("brewType", "소주")
                .when().get("/liquors")
                .then().log().all()
                .extract().jsonPath().getList("data", LiquorElementResponse.class);

        // then
        assertAll(
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getName))
                        .containsExactly("새로"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getPrice))
                        .containsExactly("3000"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getImageUrl))
                        .containsExactly("/soju-url"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getStock))
                        .containsExactly(100)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류와 지역으로 검색할 수 있다")
    void liquorListByLiquorRegion() {
        // given
        SaveLiquorRequest saveSojuRequest = new SaveLiquorRequest(
                "SOJU", "GYEONGGI_DO", "ON_SALE",
                "새로", "3000", "브랜드", "/soju-url",
                100, 12.0, 300);
        SaveLiquorRequest saveBeerRequest = new SaveLiquorRequest(
                "ETC", "CHUNGCHEONGBUK_DO", "ON_SALE",
                "하이트", "4000", "진로", "/beer-url",
                200, 24.0, 600);
        String accessToken = "accessToken";

        Liquor.saveLiquor(accessToken, saveSojuRequest);
        Liquor.saveLiquor(accessToken, saveBeerRequest);

        // when
        List<LiquorElementResponse> liquors = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("brewType", "기타주류")
                .param("regionType", "충청북도")
                .when().get("/liquors")
                .then().log().all()
                .extract().jsonPath().getList("data", LiquorElementResponse.class);

        // then
        assertAll(
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getName))
                        .containsExactly("하이트"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getPrice))
                        .containsExactly("4000"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getImageUrl))
                        .containsExactly("/beer-url"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getStock))
                        .containsExactly(200)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류, 지역, 판매 상태로 검색할 수 있다")
    void liquorListByLiquor_BrewRegionStatus() {
        // given
        SaveLiquorRequest saveSojuRequest = new SaveLiquorRequest(
                "SOJU", "GYEONGGI_DO", "STOPPED",
                "새로", "3000", "브랜드", "/soju-url",
                100, 12.0, 300);
        SaveLiquorRequest saveBeerRequest = new SaveLiquorRequest(
                "ETC", "CHUNGCHEONGBUK_DO", "ON_SALE",
                "하이트", "4000", "진로", "/beer-url",
                200, 24.0, 600);
        String accessToken = "accessToken";

        Liquor.saveLiquor(accessToken, saveSojuRequest);
        Liquor.saveLiquor(accessToken, saveBeerRequest);

        // when
        List<LiquorElementResponse> liquors = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("brewType", "소주")
                .param("regionType", "경기도")
                .param("statusType", "판매중지")
                .when().get("/liquors")
                .then().log().all()
                .extract().jsonPath().getList("data", LiquorElementResponse.class);

        // then
        assertAll(
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getName))
                        .containsExactly("새로"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getPrice))
                        .containsExactly("3000"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getImageUrl))
                        .containsExactly("/soju-url"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getStock))
                        .containsExactly(100)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류, 지역, 판매 상태, 브랜드로 검색할 수 있다")
    void liquorListByLiquor_BrewRegionStatusBrand() {
        // given
        SaveLiquorRequest saveSojuRequest = new SaveLiquorRequest(
                "SOJU", "GYEONGGI_DO", "STOPPED",
                "새로", "3000", "브랜드", "/soju-url",
                100, 12.0, 300);
        SaveLiquorRequest saveBeerRequest = new SaveLiquorRequest(
                "ETC", "CHUNGCHEONGBUK_DO", "ON_SALE",
                "하이트", "4000", "진로", "/beer-url",
                200, 24.0, 600);
        SaveLiquorRequest saveBerryRequest = new SaveLiquorRequest(
                "BERRY", "JEOLLABUK_DO", "ON_SALE",
                "얼음딸기주", "4500", "우영미", "/strawberry-url",
                20, 14.0, 400);
        String accessToken = "accessToken";

        Liquor.saveLiquor(accessToken, saveSojuRequest);
        Liquor.saveLiquor(accessToken, saveBeerRequest);
        Liquor.saveLiquor(accessToken, saveBerryRequest);

        // when
        List<LiquorElementResponse> liquors = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("brewType", "BERRY")
                .param("regionType", "JEOLLABUK_DO")
                .param("statusType", "판매중")
                .param("brand", "우영미")
                .when().get("/liquors")
                .then().log().all()
                .extract().jsonPath().getList("data", LiquorElementResponse.class);

        // then
        assertAll(
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getName))
                        .containsExactly("얼음딸기주"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getPrice))
                        .containsExactly("4500"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getImageUrl))
                        .containsExactly("/strawberry-url"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getStock))
                        .containsExactly(20)
        );
    }
}
