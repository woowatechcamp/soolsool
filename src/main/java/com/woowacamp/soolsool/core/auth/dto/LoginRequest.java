package com.woowacamp.soolsool.core.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequest {

    @Email(message = "email 형식과 맞지 않습니다.")
    @NotBlank(message = "email은 필수 입력 값입니다.")
    @Size(max = 255, message = "email 길이가 너무 깁니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(max = 60, message = "password 길이가 너무 깁니다.")
    private final String password;
}
