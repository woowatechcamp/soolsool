package com.woowacamp.soolsool.core.member.code;

import com.woowacamp.soolsool.global.common.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberResultCode implements ResultCode {

    MEMBER_CREATE_SUCCESS(200, "MS201", "회원 등록이 완료되었습니다."),
    MEMBER_FIND_SUCCESS(200, "MS202", "회원 조회가 완료되었습니다."),
    MEMBER_MODIFY_SUCCESS(200, "MS203", "회원 정보 수정 완료되었습니다."),
    MEMBER_DELETE_SUCCESS(204, "MS204", "회원 정보 삭제가 완료되었습니다."),
    MEMBER_MILEAGE_CHARGE_SUCCESS(200, "MS205", "회원 마일리지 충전이 완료되었습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
