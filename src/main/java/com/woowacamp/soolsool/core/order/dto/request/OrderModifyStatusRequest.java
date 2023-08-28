package com.woowacamp.soolsool.core.order.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderModifyStatusRequest {

    private final String status;
}
