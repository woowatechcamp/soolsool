package com.woowacamp.soolsool.core.member.dto.request;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateRequest {

    private final String memberRoleType;

    @Email(message = "email 형식과 맞지 않습니다.")
    @NotBlank(message = "email은 필수 입력 값입니다.")
    @DecimalMax(value = "255")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @DecimalMax(value = "60")
    private final String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @DecimalMax(value = "13")
    private final String name;

    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    @DecimalMax(value = "13")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다.")
    private final String phoneNumber;

    @DecimalMax(value = "255")
    private final String mileage;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    @DecimalMax(value = "100")
    private final String address;
}
