package com.woowacamp.soolsool.core.payment.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaySuccessResponse {

    private final Long orderId;
}
