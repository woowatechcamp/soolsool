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
import com.woowacamp.soolsool.acceptance.fixture.RestCartFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestLiquorFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestLiquorStockFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestMemberFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestPayFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestReceiptFixture;
import com.woowacamp.soolsool.core.liquor.dto.response.LiquorClickElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.response.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.response.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.request.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.request.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.dto.response.PageLiquorClickResponse;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@DisplayName("인수 테스트: /liquor")
class LiquorAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUpData() {
        RestMemberFixture.회원가입_최민족_판매자();
        RestMemberFixture.회원가입_김배달_구매자();
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
            .when().post("/api/liquors")
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
        Long 새로_Id = RestLiquorFixture.술_등록_새로_판매중(accessToken);

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
            .when().put("/api/liquors/{liquorId}", 새로_Id)
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
        Long 새로_Id = RestLiquorFixture.술_등록_새로_판매중(accessToken);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
            .when().delete("/api/liquors/{liquorId}", 새로_Id)
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
        String accessToken = RestAuthFixture.로그인_최민족_판매자();
        Long 새로_Id = RestLiquorFixture.술_등록_새로_판매중(accessToken);

        // when
        LiquorDetailResponse liquorDetailResponse = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .when().get("/api/liquors/{liquorId}", 새로_Id)
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
            () -> assertThat(liquorDetailResponse.getVolume()).isEqualTo(300),
            () -> assertThat(liquorDetailResponse.getRelatedLiquors()).isEmpty()
        );
    }

    @Test
    @DisplayName("술 상세정보를 연관된 상품과 함께 조회할 수 있다")
    void liquorDetailWithRelatedLiquors() {
        // given
        String vendorAccessToken = RestAuthFixture.로그인_최민족_판매자();
        Long 새로_Id = RestLiquorFixture.술_등록_새로_판매중(vendorAccessToken);
        RestLiquorStockFixture.술_재고_등록(vendorAccessToken, 새로_Id, 100);
        Long 얼음딸기주_Id = RestLiquorFixture.술_등록_과일주_전라북도_얼음딸기주_우영미_판매중(vendorAccessToken);
        RestLiquorStockFixture.술_재고_등록(vendorAccessToken, 얼음딸기주_Id, 100);

        String customerAccessToken = RestAuthFixture.로그인_김배달_구매자();
        RestCartFixture.장바구니_상품_추가(customerAccessToken, 새로_Id, 1);
        RestCartFixture.장바구니_상품_추가(customerAccessToken, 얼음딸기주_Id, 1);
        Long 주문서_Id = RestReceiptFixture.주문서_생성(customerAccessToken);
        RestPayFixture.결제_준비(customerAccessToken, 주문서_Id);
        RestPayFixture.결제_성공(customerAccessToken, 주문서_Id);

        // when
        LiquorDetailResponse liquorDetailResponse = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .when().get("/api/liquors/{liquorId}", 새로_Id)
            .then().log().all()
            .extract().jsonPath().getObject("data", LiquorDetailResponse.class);

        List<Long> relatedLiquorIds = liquorDetailResponse.getRelatedLiquors().stream()
            .map(LiquorElementResponse::getId)
            .collect(Collectors.toList());

        // then
        assertAll(
            () -> assertThat(liquorDetailResponse.getName()).isEqualTo("새로"),
            () -> assertThat(liquorDetailResponse.getPrice()).isEqualTo("3000"),
            () -> assertThat(liquorDetailResponse.getBrand()).isEqualTo("롯데"),
            () -> assertThat(liquorDetailResponse.getImageUrl()).isEqualTo("/soju-url"),
            () -> assertThat(liquorDetailResponse.getStock()).isEqualTo(99),
            () -> assertThat(liquorDetailResponse.getAlcohol()).isEqualTo(12.0),
            () -> assertThat(liquorDetailResponse.getVolume()).isEqualTo(300),
            () -> assertThat(relatedLiquorIds).containsExactlyInAnyOrder(얼음딸기주_Id)
        );
    }

    @Test
    @DisplayName("술 목록을 최신순으로 조회할 수 있다")
    void liquorList() {
        // given
        String accessToken = RestAuthFixture.로그인_최민족_판매자();
        RestLiquorFixture.술_등록_새로_판매중(accessToken);
        RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(accessToken);

        // when
        List<LiquorClickElementResponse> liquors = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .when().get("/api/liquors")
            .then().log().all()
            .extract().jsonPath().getObject("data", PageLiquorClickResponse.class).getLiquors();

        // then
        assertAll(
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getName))
                .containsExactly("하이트", "새로"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getPrice))
                .containsExactly("4000", "3000"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getImageUrl))
                .containsExactly("/beer-url", "/soju-url"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getStock))
                .containsExactly(0, 0)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류로 검색할 수 있다")
    void liquorListByLiquorType() {
        // given
        String accessToken = RestAuthFixture.로그인_최민족_판매자();
        RestLiquorFixture.술_등록_새로_판매중(accessToken);
        RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(accessToken);

        // when
        List<LiquorClickElementResponse> liquors = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .param("brew", "소주")
            .when().get("/api/liquors")
            .then().log().all()
            .extract().jsonPath().getObject("data", PageLiquorClickResponse.class).getLiquors();

        // then
        assertAll(
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getName))
                .containsExactly("새로"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getPrice))
                .containsExactly("3000"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getImageUrl))
                .containsExactly("/soju-url"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getStock))
                .containsExactly(0)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류와 지역으로 검색할 수 있다")
    void liquorListByLiquorRegion() {
        // given
        String accessToken = RestAuthFixture.로그인_최민족_판매자();
        RestLiquorFixture.술_등록_새로_판매중(accessToken);
        RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(accessToken);

        // when
        List<LiquorClickElementResponse> liquors = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .param("brew", "소주")
            .param("region", "경기도")
            .when().get("/api/liquors")
            .then().log().all()
            .extract().jsonPath().getObject("data", PageLiquorClickResponse.class).getLiquors();

        // then
        assertAll(
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getName))
                .containsExactly("새로"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getPrice))
                .containsExactly("3000"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getImageUrl))
                .containsExactly("/soju-url"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getStock))
                .containsExactly(0)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류, 지역, 판매 상태로 검색할 수 있다")
    void liquorListByLiquor_BrewRegionStatus() {
        // given
        String accessToken = RestAuthFixture.로그인_최민족_판매자();
        RestLiquorFixture.술_등록_새로_판매중(accessToken);
        RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(accessToken);

        // when
        List<LiquorClickElementResponse> liquors = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .param("brew", "ETC")
            .param("region", "경기도")
            .param("status", "판매중지")
            .when().get("/api/liquors")
            .then().log().all()
            .extract().jsonPath().getObject("data", PageLiquorClickResponse.class).getLiquors();

        // then
        assertAll(
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getName))
                .containsExactly("하이트"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getPrice))
                .containsExactly("4000"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getImageUrl))
                .containsExactly("/beer-url"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getStock))
                .containsExactly(0)
        );
    }

    @Test
    @DisplayName("술 목록을 술 종류, 지역, 판매 상태, 브랜드로 검색할 수 있다")
    void liquorListByLiquor_BrewRegionStatusBrand() {
        // given
        String accessToken = RestAuthFixture.로그인_최민족_판매자();
        RestLiquorFixture.술_등록_새로_판매중(accessToken);
        RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(accessToken);
        RestLiquorFixture.술_등록_과일주_전라북도_얼음딸기주_우영미_판매중(accessToken);

        // when
        List<LiquorClickElementResponse> liquors = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .param("brew", "BERRY")
            .param("region", "JEOLLABUK_DO")
            .param("status", "판매중")
            .param("brand", "우영미")
            .when().get("/api/liquors")
            .then().log().all()
            .extract().jsonPath().getObject("data", PageLiquorClickResponse.class).getLiquors();

        // then
        assertAll(
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getName))
                .containsExactly("얼음딸기주"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getPrice))
                .containsExactly("4500"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getImageUrl))
                .containsExactly("/strawberry-url"),
            () -> assertThat(liquors.stream().map(LiquorClickElementResponse::getStock))
                .containsExactly(0)
        );
    }
}
