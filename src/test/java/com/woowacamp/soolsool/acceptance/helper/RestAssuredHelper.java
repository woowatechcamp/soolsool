package com.woowacamp.soolsool.acceptance.helper;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class RestAssuredHelper {

    static class Liquor {

        public static ExtractableResponse<Response> saveLiquor(String accessToken, SaveLiquorRequest request) {
            return RestAssured
                    .given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .body(request)
                    .when().post("/liquors")
                    .then().log().all()
                    .extract();
        }

        class SaveLiquorRequest {

            private final String name;
            private final String price;
            private final String brand;
            private final String imageUrl;
            private final Integer stock;
            private final Double alcohol;
            private final Integer volume;

            public SaveLiquorRequest(
                    final String name,
                    final String price,
                    final String brand,
                    final String imageUrl,
                    final Integer stock,
                    final Double alcohol,
                    final Integer volume
            ) {
                this.name = name;
                this.price = price;
                this.brand = brand;
                this.imageUrl = imageUrl;
                this.stock = stock;
                this.alcohol = alcohol;
                this.volume = volume;
            }

            public String getName() {
                return name;
            }

            public String getPrice() {
                return price;
            }

            public String getBrand() {
                return brand;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public Integer getStock() {
                return stock;
            }

            public Double getAlcohol() {
                return alcohol;
            }

            public Integer getVolume() {
                return volume;
            }
        }
    }
}
