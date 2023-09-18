package com.woowacamp.soolsool.core.statistics.code;

import static org.springframework.http.HttpStatus.OK;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatisticsResultCode implements ResultCode {

    STATISTIC_TOP5_SALE_PRICE(OK.value(), "S101", "누적 판매금액이 가장 많은 술 상품 목록 top5 정상 조회 되었습니다."),
    STATISTIC_TOP5_SALE_QUANTITY(OK.value(), "S102", "누적 판매량이 가장 많은 술 상품 목록 top5 정상 조회 되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
