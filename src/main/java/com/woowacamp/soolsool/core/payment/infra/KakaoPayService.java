package com.woowacamp.soolsool.core.payment.infra;

import com.woowacamp.soolsool.core.payment.code.PayErrorCode;
import com.woowacamp.soolsool.core.payment.domain.KakaoPayReceipt;
import com.woowacamp.soolsool.core.payment.dto.PayApproveResponse;
import com.woowacamp.soolsool.core.payment.dto.response.KakaoPayApproveResponse;
import com.woowacamp.soolsool.core.payment.dto.response.KakaoPayReadyResponse;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.repository.KakaoPayReceiptRepository;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import com.woowacamp.soolsool.global.infra.SimplePayService;
import java.net.URI;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoPayService implements SimplePayService {

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
    private final KakaoPayReceiptRepository kakaoPayReceiptRepository;


    @Value("${kakao.admin.key}")
    private String adminKey;

    @Override
    @Transactional
    public PayReadyResponse payReady(final Receipt receipt) {
        HttpHeaders headers = getKakaoPayHeaders();

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
            final KakaoPayReadyResponse kakaoPayReadyResponse = restTemplate.postForObject(
                URI.create(HOST + READY_ENDPOINT), body, KakaoPayReadyResponse.class);
            if (Objects.isNull(kakaoPayReadyResponse)) {
                throw new SoolSoolException(PayErrorCode.NOT_FOUND_PAY_READY_RESPONSE);
            }

            kakaoPayReceiptRepository.save(
                KakaoPayReceipt.of(receipt.getId(), kakaoPayReadyResponse.getTid()));
            return kakaoPayReadyResponse.toReadyResponse();
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public PayApproveResponse payApprove(final Object... args) {
        final Receipt receipt = (Receipt) args[0];
        final String pgToken = (String) args[1];

        final KakaoPayReceipt kakaoPayReceipt = kakaoPayReceiptRepository
            .findByReceiptId(receipt.getId())
            .orElseThrow(() -> new SoolSoolException(PayErrorCode.NOT_FOUND_KAKAO_PAY_RECEIPT));

        HttpHeaders headers = getKakaoPayHeaders();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", CID);
        params.add("tid", kakaoPayReceipt.getTid());
        params.add("partner_order_id", receipt.getId().toString());
        params.add("partner_user_id", receipt.getMemberId().toString());
        params.add("pg_token", pgToken);
        params.add("total_amount", receipt.getPurchasedTotalPrice().toString());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);
        try {

            final KakaoPayApproveResponse kakaoPayApproveResponse = restTemplate.postForObject(
                URI.create(HOST + APPROVE_ENDPOINT), body, KakaoPayApproveResponse.class);
            if (Objects.isNull(kakaoPayApproveResponse)) {
                throw new SoolSoolException(PayErrorCode.NOT_FOUND_PAY_APPROVE_RESPONSE);
            }
            return kakaoPayApproveResponse.toApproveResponse();
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpHeaders getKakaoPayHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + adminKey);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        return headers;
    }
}
