package com.woowacamp.soolsool.core.liquor.code;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.woowacamp.soolsool.global.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorErrorCode implements ErrorCode {

    INVALID_LENGTH_BRAND(400, "L101", "술 브랜드는 20자보다 길 수 없습니다."),
    NO_CONTENT_BRAND(400, "L102", "술 브랜드는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_IMAGE_URL(400, "L103", "술 이미지 경로는 255자보다 길 수 없습니다."),
    NO_CONTENT_IMAGE_URL(400, "L104", "술 이미지 경로는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_NAME(400, "L105", "술 이름은 100자보다 길 수 없습니다."),
    NO_CONTENT_NAME(400, "L106", "술 이름은 null이거나 공백일 수 없습니다."),

    INVALID_SIZE_ALCOHOL(400, "L107", "술 도수는 0 이상 실수여야 합니다."),

    INVALID_SIZE_PRICE(400, "L108", "술 가격은 0 미만일 수 없습니다."),
    NO_CONTENT_PRICE(400, "L109", "술 가격은 null일 수 없습니다."),

    INVALID_SIZE_STOCK(400, "L110", "술 재고는 0 미만일 수 없습니다."),

    INVALID_SIZE_VOLUME(400, "L111", "술 용량은 0 미만일 수 없습니다."),

    NOT_LIQUOR_BREW_FOUND(NOT_FOUND.value(), "L112", "솔 종류가 존재하지 않습니다."),

    NOT_LIQUOR_STATUS_FOUND(NOT_FOUND.value(), "L113", "술 판매 상태가 존재하지 않습니다."),

    NOT_LIQUOR_REGION_FOUND(NOT_FOUND.value(), "L116", "술 지역이 존재하지 않습니다."),

    NOT_LIQUOR_FOUND(NOT_FOUND.value(), "L115", "술이 존재하지 않습니다."),

    NOT_LIQUOR_STOCK_FOUND(NOT_FOUND.value(), "L116", "술 재고가 존재하지 않습니다."),

    NOT_LIQUOR_CTR_FOUND(NOT_FOUND.value(), "L117", "술 클릭률이 존재하지 않습니다."),

    INTERRUPTED_THREAD(500, "L118", "예상치 못한 예외가 발생했습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
