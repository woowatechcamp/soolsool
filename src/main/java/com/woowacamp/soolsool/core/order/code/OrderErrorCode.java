package com.woowacamp.soolsool.core.order.code;

import com.woowacamp.soolsool.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    NOT_POSITIVE_QUANTITY(400, "O101", "주문 수량은 1개 이상이어야 합니다."),

    INVALID_LENGTH_BRAND(400, "O102", "술 브랜드는 20자보다 길 수 없습니다."),
    NO_CONTENT_BRAND(400, "O103", "술 브랜드는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_IMAGE_URL(400, "O104", "술 이미지 경로는 255자보다 길 수 없습니다."),
    NO_CONTENT_IMAGE_URL(400, "O105", "술 이미지 경로는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_NAME(400, "O106", "술 이름은 30자보다 길 수 없습니다."),
    NO_CONTENT_NAME(400, "O107", "술 이름은 null이거나 공백일 수 없습니다."),

    NOT_LIQUOR_STATUS_FOUND(404, "O108", "술 판매 상태가 존재하지 않습니다."),

    INVALID_PRICE_SIZE(400, "O109", "주문 가격은 0 이하일 수 없습니다."),
    NO_PRICE_CONTENT(400, "O110", "주문 가격은 null일 수 없습니다."),

    ACCESS_DENIED_ORDER(403, "O110", "본인의 주문 내역만 조회할 수 있습니다."),

    NOT_EXISTS_ORDER(404, "O111", "주문 내역이 존재하지 않습니다."),

    NOT_EXISTS_ORDER_STATUS(500, "O112", "결제 상태가 존재하지 않습니다."),

    NOT_FOUND_ORDER_STATUS(404, "P113", "주문 상태를 찾을 수 없습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
