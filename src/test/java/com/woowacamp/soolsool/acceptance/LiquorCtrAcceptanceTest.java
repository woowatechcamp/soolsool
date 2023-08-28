package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.acceptance.fixture.RestAuthFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestLiquorFixture;
import com.woowacamp.soolsool.acceptance.fixture.RestMemberFixture;
import com.woowacamp.soolsool.core.liquor.dto.response.LiquorCtrDetailResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("인수 테스트: /liquor-ctr")
class LiquorCtrAcceptanceTest extends AcceptanceTest {

    Long 새로;

    @BeforeEach
    void setUpData() {
        RestMemberFixture.회원가입_최민족_판매자();

        String 최민족_토큰 = RestAuthFixture.로그인_최민족_판매자();
        새로 = RestLiquorFixture.술_등록_새로_판매중(최민족_토큰);
    }

    @Test
    @DisplayName("주류 클릭률을 조회한다.")
    void getLiquorCtr() {
        /* given */
        RestLiquorFixture.술_목록_조회();
        RestLiquorFixture.술_목록_조회();
        RestLiquorFixture.술_목록_조회();
        RestLiquorFixture.술_상세_조회(새로);

        /* when */
        LiquorCtrDetailResponse response = RestAssured
            .given().log().all()
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .param("liquorId", 새로)
            .when().get("/liquor-ctr")
            .then().log().all()
            .extract().jsonPath().getObject("data", LiquorCtrDetailResponse.class);

        /* then */
        assertThat(response.getCtr()).isEqualTo(0.33);
    }
}
