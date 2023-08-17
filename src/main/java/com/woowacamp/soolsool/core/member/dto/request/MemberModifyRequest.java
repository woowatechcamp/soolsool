package com.woowacamp.soolsool.core.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
//@RequiredArgsConstructor
public class MemberModifyRequest {

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(max = 60, message = "password 길이가 너무 깁니다.")
    private final String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 13, message = "이름이 너무 깁니다.")
    private final String name;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    @Size(max = 100, message = "주소 길이가 너무 깁니다.")
    private final String address;

    @Builder
    public MemberModifyRequest(
        final String password,
        final String name,
        final String address
    ) {
        this.password = password;
        this.name = name;
        this.address = address;
    }
}
