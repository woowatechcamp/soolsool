package com.woowacamp.soolsool.core.member.dto.request;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberModifyRequest {

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @DecimalMax(value = "60")
    private final String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @DecimalMax(value = "13")
    private final String name;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    @DecimalMax(value = "100")
    private final String address;
}
