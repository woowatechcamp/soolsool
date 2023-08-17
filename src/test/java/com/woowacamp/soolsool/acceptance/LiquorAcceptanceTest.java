package com.woowacamp.soolsool.acceptance;

import static com.woowacamp.soolsool.global.common.LiquorResultCode.LIQUOR_CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.woowacamp.soolsool.acceptance.helper.RestAssuredHelper.Liquor;
import com.woowacamp.soolsool.core.liquor.dto.ModifyLiquorRequest;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
    @DisplayName("술 상품을 수정할 수 있다.")
    void modifyLiquorTest() {
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
        ExtractableResponse<Response> saveLiquor = Liquor.saveLiquor(accessToken,
            saveLiquorRequest);
        ModifyLiquorRequest modifyLiquorRequest = new ModifyLiquorRequest(
            "SOJU",
            "GYEONGGI_DO",
            "ON_SALE",
            "안동소주",
            "일품",
            "브랜드",
            "soolsool.png",
            100,
            12.0,
            1
        );

        // when
        ExtractableResponse<Response> response = Liquor.modifyLiquor(
            accessToken,
            modifyLiquorRequest
        );
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

}
