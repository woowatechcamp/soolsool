package com.woowacamp.soolsool.acceptance;

import static com.woowacamp.soolsool.global.common.LiquorResultCode.LIQUOR_CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.CREATED;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacamp.soolsool.acceptance.helper.RestAssuredHelper.Liquor;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
                "SOJU",
                "GYEONGGI_DO",
                "ON_SALE",
                "새로",
                "3000",
                "브랜드",
                "/url",
                100,
                12.0,
                300);

        String location = Liquor.saveLiquor("accessToken", saveLiquorRequest).header("Location");

        // when
        LiquorDetailResponse liquorDetailResponse = Liquor.liquorDetail(location).jsonPath()
                .getObject("data", LiquorDetailResponse.class);

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
                "SOJU",
                "GYEONGGI_DO",
                "ON_SALE",
                "새로",
                "3000",
                "브랜드",
                "/soju-url",
                100,
                12.0,
                300);
        SaveLiquorRequest saveBeerRequest = new SaveLiquorRequest(
                "SOJU",
                "GYEONGGI_DO",
                "ON_SALE",
                "하이트",
                "4000",
                "진로",
                "/beer-url",
                200,
                24.0,
                600);
        String accessToken = "accessToken";

        Liquor.saveLiquor(accessToken, saveSojuRequest);
        Liquor.saveLiquor(accessToken, saveBeerRequest);

        // when
        List<LiquorElementResponse> liquors = Liquor.liquorList().jsonPath().getList("data",
                LiquorElementResponse.class);
        System.out.println(liquors.stream().map(LiquorElementResponse::getName).collect(Collectors.toList()));
        // then
        assertAll(
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getName))
                        .containsExactly("하이트", "새로"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getPrice))
                        .containsExactlyInAnyOrder("4000", "3000"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getImageUrl))
                        .containsExactlyInAnyOrder("/beer-url", "/soju-url"),
                () -> assertThat(liquors.stream().map(LiquorElementResponse::getStock))
                        .containsExactlyInAnyOrder(200, 100)
        );
    }
}
