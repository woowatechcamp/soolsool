package com.woowacamp.soolsool.core.member.code;

import com.woowacamp.soolsool.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    INVALID_LENGTH_ADDRESS(400, "M101", "회원 주소는 100자보다 길 수 없습니다."),
    NO_CONTENT_ADDRESS(400, "M102", "회원 주소는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_EMAIL(400, "M103", "회원 이메일은 255자보다 길 수 없습니다."),
    NO_CONTENT_EMAIL(400, "M104", "회원 이메일은 null이거나 공백일 수 없습니다."),
    INVALID_FORMAT_EMAIL(400, "M105", "회원 이메일이 표준 이메일 양식에 맞지 않습니다."),

    NO_CONTENT_MILEAGE(400, "M106", "회원 마일리지는 null일 수 없습니다."),
    INVALID_SIZE_MILEAGE(400, "M107", "회원 마일리지는 0 미만일 수 없습니다."),

    INVALID_LENGTH_NAME(400, "M108", "회원 이름은 13자보다 길 수 없습니다."),
    NO_CONTENT_NAME(400, "M109", "회원 이름은 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_PASSWORD(400, "M110", "회원 비밀번호은 60자보다 길 수 없습니다."),
    NO_CONTENT_PASSWORD(400, "M111", "회원 비밀번호는 null이거나 공백일 수 없습니다."),

    INVALID_LENGTH_PHONE_NUMBER(400, "M112", "회원 전화번호는 13자보다 길 수 없습니다."),
    NO_CONTENT_PHONE_NUMBER(400, "M113", "회원 전화번호는 null이거나 공백일 수 없습니다."),

    MEMBER_NO_INFORMATION(400, "M101", "회원 정보를 찾을 수 없습니다."),
    MEMBER_NO_ROLE_TYPE(400, "M101", "일치하는 회원 타입이 없습니다."),
    MEMBER_NO_MATCH_PASSWORD(400, "M101", "비밀번호가 일치하지 않습니다"),
    MEMBER_DUPLICATED_EMAIL(400, "M115", "이미 존재하는 이메일 입니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
