package com.woowacamp.soolsool.core.liquor.code;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.woowacamp.soolsool.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquorErrorCode implements ErrorCode {

    INVALID_LENGTH_BRAND(400, "L101", "술 브랜드는 20자보다 길 수 없습니다."),
    NO_CONTENT_BRAND(400, "L102", "술 브랜드는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_IMAGE_URL(400, "L103", "술 이미지 경로는 255자보다 길 수 없습니다."),
    NO_CONTENT_IMAGE_URL(400, "L104", "술 이미지 경로는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_NAME(400, "L105", "술 이름은 30자보다 길 수 없습니다."),
    NO_CONTENT_NAME(400, "L106", "술 이름은 null이거나 공백일 수 없습니다."),

    NOT_LIQUOR_BREW_FOUND(NOT_FOUND.value(), "L101", "솔 종류가 존재하지 않습니다."),

    NOT_LIQUOR_STATUS_FOUND(NOT_FOUND.value(), "L102", "술 판매 상태가 존재하지 않습니다."),

    NOT_LIQUOR_REGION_FOUND(NOT_FOUND.value(), "L103", "술 지역이 존재하지 않습니다."),

    NOT_LIQUOR_FOUND(NOT_FOUND.value(), "L104", "술이 존재하지 않습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
