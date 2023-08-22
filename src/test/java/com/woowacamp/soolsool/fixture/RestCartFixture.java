package com.woowacamp.soolsool.fixture;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.core.cart.dto.request.CartItemSaveRequest;
import com.woowacamp.soolsool.core.cart.dto.response.CartItemResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import java.util.List;

public abstract class RestCartFixture {

    public static Long 장바구니_상품_추가(String 토큰, Long 상품_Id, Integer 상품_개수) {
        CartItemSaveRequest cartItemSaveRequest = new CartItemSaveRequest(상품_Id, 상품_개수);

        return RestAssured
                .given().log().all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Bearer " + 토큰)
                .body(cartItemSaveRequest)
                .when().post("/cart-items")
                .then().log().all()
                .extract().jsonPath().getObject("data", Long.class);
    }

    public static List<CartItemResponse> 장바구니_모두_조회(String 토큰) {
        return RestAssured
                .given().log().all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Bearer " + 토큰)
                .when().get("/cart-items/")
                .then().log().all()
                .extract().jsonPath().getObject("data", new TypeRef<>() {
                });
    }
}