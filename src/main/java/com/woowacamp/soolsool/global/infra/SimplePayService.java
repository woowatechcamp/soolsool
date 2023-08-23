package com.woowacamp.soolsool.global.infra;

import com.woowacamp.soolsool.core.payment.dto.PayApproveResponse;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;

public interface SimplePayService {

    PayReadyResponse payReady(final Receipt receipt);

    PayApproveResponse payApprove(final Object... args);
}
