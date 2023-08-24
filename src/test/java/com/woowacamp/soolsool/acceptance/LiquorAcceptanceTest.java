package com.woowacamp.soolsool.acceptance;

import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_CREATED;
import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_DELETED;
import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_UPDATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.acceptance.fixture.RestAuthFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestLiquorFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestMemberFixture;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@DisplayName("인수 테스트: liquor")
class LiquorAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUpData() {
        RestMemberFixture.회원가입_최민족_판매자();
    }

    @Test
    @DisplayName("술 상품을 등록할 수 있다")
    void saveLiquor() {
        // given
        String accessToken = RestAuthFixture.로그인_최민족_판매자();
        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "새로", "3000", "브랜드", "/soju-url",
            12.0, 300);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
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
        String accessToken = RestAuthFixture.로그인_최민족_판매자();
        final Long 새로_Id = RestLiquorFixture.술_등록_새로_판매중(accessToken);

        LiquorModifyRequest liquorModifyRequest = new LiquorModifyRequest(
            "SOJU", "GYEONGGI_DO", "ON_SALE",
            "안동소주", "3000", "브랜드", "soolsool.png",
            100, 12.0, 1,
            LocalDateTime.now().plusYears(10L)
        );

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
            .body(liquorModifyRequest)
            .when().put("/liquors/{liquorId}", 새로_Id)
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
        String accessToken = RestAuthFixture.로그인_최민족_판매자();
        final Long 새로_Id = RestLiquorFixture.술_등록_새로_판매중(accessToken);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
            .when().delete("/liquors/{liquorId}", 새로_Id)
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(new TypeRef<ApiResponse<Void>>() {
        }).getMessage()).isEqualTo(LIQUOR_DELETED.getMessage());
    }

    @Test
    @DisplayName("술 상세정보를 조회할 수 있다")
    void liquorDetail() {
        // given
        final String accessToken = RestAuthFixture.로그인_최민족_판매자();
        final Long 새로_Id = RestLiquorFixture.술_등록_새로_판매중(accessToken);

        // when
        LiquorDetailResponse liquorDetailResponse = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .when().get("/liquors/" + 새로_Id)
            .then().log().all()
            .extract().jsonPath().getObject("data", LiquorDetailResponse.class);

        // then
        assertAll(
            () -> assertThat(liquorDetailResponse.getName()).isEqualTo("새로"),
            () -> assertThat(liquorDetailResponse.getPrice()).isEqualTo("3000"),
            () -> assertThat(liquorDetailResponse.getBrand()).isEqualTo("롯데"),
            () -> assertThat(liquorDetailResponse.getImageUrl()).isEqualTo("/soju-url"),
            () -> assertThat(liquorDetailResponse.getStock()).isZero(),
            () -> assertThat(liquorDetailResponse.getAlcohol()).isEqualTo(12.0),
            () -> assertThat(liquorDetailResponse.getVolume()).isEqualTo(300)
        );
    }

    @Test
    @DisplayName("술 목록을 최신순으로 조회할 수 있다")
    void liquorList() {
        // given
        final String accessToken = RestAuthFixture.로그인_최민족_판매자();
        RestLiquorFixture.술_등록_새로_판매중(accessToken);
        RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(accessToken);

        // when
        List<LiquorElementResponse> liquors = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
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
                .containsExactly(0, 0)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류로 검색할 수 있다")
    void liquorListByLiquorType() {
        // given
        final String accessToken = RestAuthFixture.로그인_최민족_판매자();
        RestLiquorFixture.술_등록_새로_판매중(accessToken);
        RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(accessToken);

        // when
        List<LiquorElementResponse> liquors = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .param("brew", "소주")
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
                .containsExactly(0)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류와 지역으로 검색할 수 있다")
    void liquorListByLiquorRegion() {
        // given
        final String accessToken = RestAuthFixture.로그인_최민족_판매자();
        RestLiquorFixture.술_등록_새로_판매중(accessToken);
        RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(accessToken);

        // when
        List<LiquorElementResponse> liquors = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .param("brew", "소주")
            .param("region", "경기도")
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
                .containsExactly(0)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류, 지역, 판매 상태로 검색할 수 있다")
    void liquorListByLiquor_BrewRegionStatus() {
        // given
        final String accessToken = RestAuthFixture.로그인_최민족_판매자();
        RestLiquorFixture.술_등록_새로_판매중(accessToken);
        RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(accessToken);

        // when
        List<LiquorElementResponse> liquors = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .param("brew", "ETC")
            .param("region", "경기도")
            .param("status", "판매중지")
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
                .containsExactly(0)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류, 지역, 판매 상태, 브랜드로 검색할 수 있다")
    void liquorListByLiquor_BrewRegionStatusBrand() {
        // given
        final String accessToken = RestAuthFixture.로그인_최민족_판매자();
        RestLiquorFixture.술_등록_새로_판매중(accessToken);
        RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(accessToken);
        RestLiquorFixture.술_등록_과일주_전라북도_얼음딸기주_우영미_판매중(accessToken);

        // when
        List<LiquorElementResponse> liquors = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .param("brew", "BERRY")
            .param("region", "JEOLLABUK_DO")
            .param("status", "판매중")
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
                .containsExactly(0)
        );
    }


}
