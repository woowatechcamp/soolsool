package com.woowacamp.soolsool.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.domain.vo.OrderStatusType;
import com.woowacamp.soolsool.core.order.dto.response.OrderListResponse;
import com.woowacamp.soolsool.core.order.repository.OrderRepository;
import com.woowacamp.soolsool.core.order.repository.OrderStatusRepository;
import com.woowacamp.soolsool.core.order.service.OrderService;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptPrice;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptQuantity;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptRepository;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptStatusRepository;
import com.woowacamp.soolsool.global.auth.dto.LoginRequest;
import com.woowacamp.soolsool.global.auth.dto.LoginResponse;
import com.woowacamp.soolsool.global.common.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Disabled("인수 테스트 방법 변경")
@DisplayName("인수 테스트: order")
class OrderAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptStatusRepository receiptStatusRepository;

    @Autowired
    private LiquorRegionRepository liquorRegionRepository;

    @Autowired
    private LiquorBrewRepository liquorBrewRepository;

    private Order firstMemberOrder1;
    private Order firstMemberOrder2;
    private Order secondMemberOrder1;

    @BeforeEach
    void setUpOrder() {
        firstMemberOrder1 = createOrder(1L, 1L);
        firstMemberOrder2 = createOrder(1L, 2L);
        secondMemberOrder1 = createOrder(2L, 3L);
    }

    // TODO: Fixture
    private String findToken(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        ExtractableResponse<Response> loginResponse = RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(loginRequest).log().all()
            .when().post("/auth/login")
            .then().log().all()
            .extract();

        return "Bearer " + loginResponse.body().as(new TypeRef<ApiResponse<LoginResponse>>() {
            })
            .getData()
            .getAccessToken();
    }

    @Test
    @DisplayName("Order 상세내역을 조회한다.")
    void orderDetail() {
        // given
        String accessToken = findToken("woowafriends@naver.com", "woowa");

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .when().get("/orders/" + firstMemberOrder1.getId())
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("다른 사용자의 Order 상세내역을 조회할 경우 403을 반환한다.")
    void orderOthersDetail() {
        // given
        String accessToken = findToken("woowafriends@naver.com", "woowa");

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .when().get("/orders/" + secondMemberOrder1.getId())
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Order 상세내역이 없을 경우 404를 반환한다.")
    void orderNotExistsDetail() {
        // given
        String accessToken = findToken("woowafriends@naver.com", "woowa");

        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .when().get("/orders/99999")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Order 리스트를 조회한다.")
    void orderList() {
        // given
        String accessToken = findToken("woowafriends@naver.com", "woowa");

        // when
        List<OrderListResponse> data = RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .when().get("/orders")
            .then().log().all()
            .extract().jsonPath().getList("data", OrderListResponse.class);

        // then
        assertThat(data).hasSize(2);
    }

    private Order createOrder(final Long memberId, final Long liquorId) {
        Receipt receipt = new Receipt(
            memberId,
            receiptStatusRepository.findByType(ReceiptStatusType.COMPLETED).get(),
            new ReceiptPrice(new BigInteger("10000")),
            new ReceiptPrice(new BigInteger("1000")),
            new ReceiptPrice(new BigInteger("9000")),
            new ReceiptQuantity(10),
            new ArrayList<>(List.of())
        );

        ReceiptItem receiptItem = new ReceiptItem(
            receipt,
            liquorId,
            liquorBrewRepository.findByType(LiquorBrewType.SOJU).get(),
            liquorRegionRepository.findByType(LiquorRegionType.GYEONGSANGNAM_DO).get(),
            "안동 소주", "10000", "10000", "안동", "/soju.jpeg",
            21.7, 400, 1
        );

        receipt.addReceiptItems(List.of(receiptItem));

        receiptRepository.save(receipt);

        Order order = new Order(
            memberId,
            orderStatusRepository.findByType(OrderStatusType.COMPLETED).get(),
            receipt
        );

        return orderRepository.save(order);
    }
}
