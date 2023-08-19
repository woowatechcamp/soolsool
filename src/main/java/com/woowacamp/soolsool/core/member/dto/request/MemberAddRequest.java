package com.woowacamp.soolsool.core.member.dto.request;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberAddRequest {

    private final MemberRoleType memberRoleType;

    @Email(message = "email 형식과 맞지 않습니다.")
    @NotBlank(message = "email은 필수 입력 값입니다.")
    @Size(max = 255, message = "email 길이가 너무 깁니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(max = 60, message = "password 길이가 너무 깁니다.")
    private final String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 13, message = "이름이 너무 깁니다.")
    private final String name;

    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    @Size(max = 13, message = "전화번호 길이가 너무 깁니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다.")
    private final String phoneNumber;

    @Size(max = 255, message = "불가능한 마일리지 액수 입니다.")
    private final String mileage;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    @Size(max = 100, message = "주소 길이가 너무 깁니다.")
    private final String address;

    public Member toMember(final MemberRole memberRole) {
        return Member.builder()
            .role(memberRole)
            .email(this.email)
            .password(this.password)
            .name(this.name)
            .phoneNumber(this.phoneNumber)
            .mileage(this.mileage)
            .address(this.address)
            .build();
    }
}
