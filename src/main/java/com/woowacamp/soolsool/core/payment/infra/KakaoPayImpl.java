package com.woowacamp.soolsool.core.payment.infra;

import com.woowacamp.soolsool.core.payment.dto.response.KakaoPayApproveResponse;
import com.woowacamp.soolsool.core.payment.dto.response.KakaoPayReadyResponse;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoPayImpl {

    private static final String HOST = "https://kapi.kakao.com";
    private static final String READY_ENDPOINT = "/v1/payment/ready";
    private static final String APPROVE_ENDPOINT = "/v1/payment/approve";
    // TODO: 가맹점 번호, 어드민 키 주입하기
    private static final String CID = "TC0ONETIME";
    private static final String DEFAULT_URL = "http://localhost:8080";
    private static final String APPROVE_URL = "/pay/success";
    private static final String CANCEL_URL = "/pay/cancel";
    private static final String FAIL_URL = "/pay/fail";

    private final RestTemplate restTemplate;

    @Value("${kakao.admin.key}")
    private String adminKey;

    public KakaoPayReadyResponse payReady(final Receipt receipt) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + adminKey);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", CID);
        params.add("partner_order_id", receipt.getId().toString());
        params.add("partner_user_id", receipt.getMemberId().toString());
        // TODO : 여러개 일 때 외 한건 붙이기
        params.add("item_name", receipt.getReceiptItems().get(0).toString());
        params.add("quantity", String.valueOf(receipt.getTotalQuantity()));
        params.add("total_amount", receipt.getPurchasedTotalPrice().toString());
        params.add("tax_free_amount", "0");
        params.add("approval_url", DEFAULT_URL + APPROVE_URL + "/" + receipt.getId());
        params.add("cancel_url", DEFAULT_URL + CANCEL_URL + "/" + receipt.getId());
        params.add("fail_url", DEFAULT_URL + FAIL_URL + "/" + receipt.getId());
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            return restTemplate.postForObject(
                URI.create(HOST + READY_ENDPOINT), body, KakaoPayReadyResponse.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    public KakaoPayApproveResponse payApprove(final String tid, final String pgToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + adminKey);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", CID);
        params.add("tid", tid);
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "gorany");
        params.add("pg_token", pgToken);
        params.add("total_amount", "2100");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);
        try {
            return restTemplate.postForObject(
                URI.create(HOST + APPROVE_ENDPOINT), body, KakaoPayApproveResponse.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
