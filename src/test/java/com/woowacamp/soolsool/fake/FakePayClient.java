package com.woowacamp.soolsool.fake;

import com.woowacamp.soolsool.core.payment.dto.response.PayApproveResponse;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.infra.PayClient;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class FakePayClient implements PayClient {

    @Override
    public PayReadyResponse ready(final Receipt receipt) {
        return new PayReadyResponse(
            "1",
            "http://pc-url",
            "http://mobile-url",
            "http://app-url"
        );
    }

    @Override
    public PayApproveResponse payApprove(final Object... args) {
        return new PayApproveResponse(
            "1",
            "1",
            "1",
            "1",
            "1",
            "1"
        );
    }
}
