package com.woowacamp.soolsool.core.order.service;

import java.math.BigInteger;

public interface OrderMemberService {

    void refundMileage(final Long memberId, final BigInteger mileage);
}

