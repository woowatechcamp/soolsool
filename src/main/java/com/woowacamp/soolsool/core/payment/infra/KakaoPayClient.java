package com.woowacamp.soolsool.core.payment.infra;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacamp.soolsool.core.payment.code.PayErrorCode;
import com.woowacamp.soolsool.core.payment.domain.KakaoPayReceipt;
import com.woowacamp.soolsool.core.payment.dto.response.PayApproveResponse;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.infra.dto.response.KakaoPayApproveResponse;
import com.woowacamp.soolsool.core.payment.infra.dto.response.KakaoPayReadyResponse;
import com.woowacamp.soolsool.core.payment.repository.KakaoPayReceiptRepository;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoPayClient implements PayClient {

    private static final String HOST = "https://kapi.kakao.com";
    private static final String READY_ENDPOINT = "/v1/payment/ready";
    private static final String APPROVE_ENDPOINT = "/v1/payment/approve";
    private static final String CID = "TC0ONETIME";
    private static final String DEFAULT_URL = "http://localhost:3000";
    private static final String APPROVE_URL = "/pay/success";
    private static final String CANCEL_URL = "/pay/cancel";
    private static final String FAIL_URL = "/pay/fail";

    private final RestTemplate restTemplate;
    private final KakaoPayReceiptRepository kakaoPayReceiptRepository;


    @Value("${kakao.admin.key}")
    private String adminKey;

    @Override
    @Transactional
    public PayReadyResponse ready(final Receipt receipt) {
        final HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(
            getKakaoPayReadyBody(receipt),
            getKakaoPayHeaders()
        );

        final KakaoPayReadyResponse kakaoPayReadyResponse = readyForKakaoPay(body);

        kakaoPayReceiptRepository.save(
            KakaoPayReceipt.of(receipt.getId(), kakaoPayReadyResponse.getTid()));

        return kakaoPayReadyResponse.toReadyResponse();
    }

    private MultiValueMap<String, String> getKakaoPayReadyBody(final Receipt receipt) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("partner_order_id", receipt.getId().toString());
        params.add("partner_user_id", receipt.getMemberId().toString());
        params.add("item_name", generateItemName(receipt.getReceiptItems()));
        params.add("quantity", String.valueOf(receipt.getTotalQuantity()));
        params.add("total_amount", receipt.getPurchasedTotalPrice().toString());
        params.add("tax_free_amount", "0");
        params.add("approval_url", DEFAULT_URL + APPROVE_URL + "/" + receipt.getId());
        params.add("cancel_url", DEFAULT_URL + CANCEL_URL + "/" + receipt.getId());
        params.add("fail_url", DEFAULT_URL + FAIL_URL + "/" + receipt.getId());
        return params;
    }

    private String generateItemName(final List<ReceiptItem> receiptItems) {
        if (receiptItems.size() == 1) {
            return receiptItems.get(0).getReceiptItemName();
        }

        return receiptItems.get(0).getReceiptItemName() + " 외 " + (receiptItems.size() - 1) + "건";
    }

    private KakaoPayReadyResponse readyForKakaoPay(
        final HttpEntity<MultiValueMap<String, String>> body
    ) {
        final KakaoPayReadyResponse kakaoPayReadyResponse = restTemplate.postForObject(
            URI.create(HOST + READY_ENDPOINT), body, KakaoPayReadyResponse.class
        );

        if (Objects.isNull(kakaoPayReadyResponse)) {
            throw new SoolSoolException(PayErrorCode.NOT_FOUND_PAY_READY_RESPONSE);
        }

        return kakaoPayReadyResponse;
    }

    @Transactional(readOnly = true)
    public PayApproveResponse payApprove(final Object... args) {
        final Receipt receipt = (Receipt) args[0];
        final String pgToken = (String) args[1];

        final KakaoPayReceipt kakaoPayReceipt = kakaoPayReceiptRepository
            .findByReceiptId(receipt.getId())
            .orElseThrow(() -> new SoolSoolException(PayErrorCode.NOT_FOUND_KAKAO_PAY_RECEIPT));

        final HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(
            getKakaoPayApproveBody(receipt, pgToken, kakaoPayReceipt),
            getKakaoPayHeaders()
        );

        final KakaoPayApproveResponse kakaoPayApproveResponse = approveForKakaoPay(body);

        return kakaoPayApproveResponse.toPayApproveResponse();
    }

    private KakaoPayApproveResponse approveForKakaoPay(
        final HttpEntity<MultiValueMap<String, String>> body
    ) {
        final KakaoPayApproveResponse kakaoPayApproveResponse = restTemplate.postForObject(
            URI.create(HOST + APPROVE_ENDPOINT), body, KakaoPayApproveResponse.class);

        if (Objects.isNull(kakaoPayApproveResponse)) {
            throw new SoolSoolException(PayErrorCode.NOT_FOUND_PAY_APPROVE_RESPONSE);
        }

        return kakaoPayApproveResponse;
    }

    private MultiValueMap<String, String> getKakaoPayApproveBody(
        final Receipt receipt,
        final String pgToken,
        final KakaoPayReceipt kakaoPayReceipt
    ) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", CID);
        params.add("tid", kakaoPayReceipt.getTid());
        params.add("partner_order_id", receipt.getId().toString());
        params.add("partner_user_id", receipt.getMemberId().toString());
        params.add("pg_token", pgToken);
        params.add("total_amount", receipt.getPurchasedTotalPrice().toString());
        return params;
    }

    private HttpHeaders getKakaoPayHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, "KakaoAK " + adminKey);
        headers.add(ACCEPT, APPLICATION_JSON_VALUE);
        headers.add(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        return headers;
    }
}
