package com.woowacamp.soolsool.core.receipt.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.woowacamp.soolsool.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptErrorCode implements ErrorCode {

    INVALID_LENGTH_BRAND(400, "R101", "주문서 술 브랜드는 20자보다 길 수 없습니다."),
    NO_CONTENT_BRAND(400, "R102", "주문서 술 브랜드는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_IMAGE_URL(400, "R103", "주문서 술 이미지 경로는 255자보다 길 수 없습니다."),
    NO_CONTENT_IMAGE_URL(400, "R104", "주문서 술 이미지 경로는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_NAME(400, "R105", "주문서 술 이름은 100자보다 길 수 없습니다."),
    NO_CONTENT_NAME(400, "R106", "주문서 술 이름은 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_BREW(400, "R105", "주문서 술 종류는 20자보다 길 수 없습니다."),
    NO_CONTENT_BREW(400, "R106", "주문서 술 종류는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_REGION(400, "R105", "주문서 술 지역은 20자보다 길 수 없습니다."),
    NO_CONTENT_REGION(400, "R106", "주문서 술 지역은 null이거나 공백일 수 없습니다."),

    INVALID_SIZE_ALCOHOL(400, "R107", "주문서 술 도수는 0 이상 실수여야 합니다."),

    INVALID_SIZE_PRICE(400, "R108", "주문서 술 가격은 0 미만일 수 없습니다."),
    NO_CONTENT_PRICE(400, "R109", "주문서 술 가격은 null일 수 없습니다."),

    INVALID_SIZE_STOCK(400, "R110", "주문서 술 재고는 0 미만일 수 없습니다."),

    INVALID_SIZE_VOLUME(400, "R111", "주문서 술 용량은 0 미만일 수 없습니다."),

    INVALID_QUANTITY_SIZE(400, "R103", "주문서 술 개수는 0 미만일 수 없습니다."),

    NOT_RECEIPT_FOUND(NOT_FOUND.value(), "R115", "주문서가 존재하지 않습니다."),
    NOT_EQUALS_MEMBER(BAD_REQUEST.value(), "R116", "다른 사용자의 주문서를 가지고 있습니다."),

    NOT_RECEIPT_TYPE_FOUND(NOT_FOUND.value(), "R117", "주문서 상태가 존재하지 않습니다."),
    UNMODIFIABLE_STATUS(BAD_REQUEST.value(), "R122", "진행중인 주문서만 변경할 수 있습니다."),

    MEMBER_NO_INFORMATION(400, "R118", "회원 정보를 찾을 수 없습니다."),

    ACCESS_DENIED_RECEIPT(403, "R119", "본인의 주문서 내역만 조회할 수 있습니다."),
    NOT_FOUND_RECEIPT(404, "R120", "회원의 주문 내역을 찾을 수 없습니다."),

    INTERRUPTED_THREAD(500, "R121", "예상치 못한 예외가 발생했습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
