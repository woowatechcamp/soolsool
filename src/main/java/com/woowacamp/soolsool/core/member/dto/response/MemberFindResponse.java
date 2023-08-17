package com.woowacamp.soolsool.core.member.dto.response;

import com.woowacamp.soolsool.core.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFindResponse {

    private final String roleName;
    private final String email;
    private final String name;
    private final String mileage;
    private final String address;

    @Builder
    public MemberFindResponse(
        String roleName,
        String email,
        String name,
        String mileage,
        String address
    ) {
        this.roleName = roleName;
        this.email = email;
        this.name = name;
        this.mileage = mileage;
        this.address = address;
    }

    public static MemberFindResponse of(Member member) {
        return MemberFindResponse.builder()
            .roleName(member.getRole().getName().getType())
            .email(member.getEmail().getEmail())
            .name(member.getName().getName())
            .mileage(member.getMileage().getMileage().toString())
            .address(member.getAddress().getAddress())
            .build();
    }
}
