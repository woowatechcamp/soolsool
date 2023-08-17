package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.acceptance.helper.RestAssuredHelper.Liquor;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("술 상품 관련 기능")
class LiquorAcceptanceTest extends AcceptanceTest {


    @Test
    @DisplayName("술 상품을 등록할 수 있다")
    void saveLiquor() {
        // given
        SaveLiquorRequest saveLiquorRequest = new SaveLiquorRequest(
            "소주",
            "경기도",
            "판매중",
            "새로", "3000",
            "브랜드", "/url",
            100, 12.0,
            300);

        // when
        ExtractableResponse<Response> response = Liquor.saveLiquor(
            "1234",
            saveLiquorRequest
        );

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
