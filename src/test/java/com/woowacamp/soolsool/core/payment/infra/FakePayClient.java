package com.woowacamp.soolsool.core.payment.infra;

import com.woowacamp.soolsool.core.payment.domain.Payment;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
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
            "http://지들끼리이야기하는URL",
            "http://지들끼리이야기하는URL",
            "http://지들끼리이야기하는URL"
        );
    }

    @Override
    public Payment payApprove(final Object... args) {
        return null;
    }
}
