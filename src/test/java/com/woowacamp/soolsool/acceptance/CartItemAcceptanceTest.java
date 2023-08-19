package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("장바구니 : 인수 테스트")
class CartItemAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("성공 : 장바구니에 술 추가")
    void createMember() {
        // given
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
            .memberRoleType("CUSTOMER")
            .email("test@email.com")
            .password("test_password")
            .name("최배달")
            .phoneNumber("010-1234-5678")
            .mileage("0")
            .address("서울시 잠실역")
            .build();
        RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(memberAddRequest)
            .when().post("/members")
            .then().log().all();

        // TODO: 로그인 구현 이후 로그인 로직 추가 후 정상 accessToken 조회하기
        String accessToken = "accessToken";

        // TODO: 상품 추가, 조회 구현 이후 아래 주석 해제
        Long liquorId = 1L;
//        LiquorSaveRequest liquorSaveRequest = new LiquorSaveRequest(
//            "SOJU", "GYEONGSANGNAM_DO", "ON_SALE",
//            "안동소주", "12000", "안동", "/soju.jpeg",
//            120, 31.3, 300
//        );
//        String location = RestAssured
//            .given().log().all()
//            .contentType(MediaType.APPLICATION_JSON_VALUE)
//            .body(liquorSaveRequest)
//            .when().post("/liquors")
//            .then().log().all()
//            .extract().header("Location");
//        Long liquorId = RestAssured
//            .given().log().all()
//            .accept(MediaType.APPLICATION_JSON_VALUE)
//            .when().get(location)
//            .then().log().all()
//            .extract().jsonPath().getObject("data", LiquorDetailResponse.class).getId();

        // when
        CartItemSaveRequest cartItemSaveRequest = new CartItemSaveRequest(liquorId, 1);
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .body(cartItemSaveRequest)
            .when().post("/cart-items")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    // TODO: 상품 추가 구현 이후 LiquorSaveRequest를 import 하고 하단의 임시 DTO 삭제
    class LiquorSaveRequest {

        private String typeName;
        private String regionName;
        private String statusName;
        private String name;
        private String price;
        private String brand;
        private String imageUrl;
        private Integer stock;
        private Double alcohol;
        private Integer volume;

        public LiquorSaveRequest(String typeName, String regionName, String statusName, String name,
            String price, String brand, String imageUrl, Integer stock, Double alcohol,
            Integer volume) {
            this.typeName = typeName;
            this.regionName = regionName;
            this.statusName = statusName;
            this.name = name;
            this.price = price;
            this.brand = brand;
            this.imageUrl = imageUrl;
            this.stock = stock;
            this.alcohol = alcohol;
            this.volume = volume;
        }
    }

    // TODO: 상품 조회 구현 이후 LiquorDetailResponse를 import 하고 하단의 임시 DTO 삭제
    class LiquorDetailResponse {

        private Long id;
        private String name;
        private String price;
        private String brand;
        private String imageUrl;
        private Integer stock;
        private Double alcohol;
        private Integer volume;

        public Long getId() {
            return id;
        }
    }
}
