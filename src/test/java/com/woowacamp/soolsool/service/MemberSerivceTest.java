package com.woowacamp.soolsool.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import com.woowacamp.soolsool.core.member.dto.request.MemberCreateRequest;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.member.repository.MemberRoleRepository;
import com.woowacamp.soolsool.core.member.service.MemberService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("멤버 : 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class MemberSerivceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberRoleRepository memberRoleRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("성공 : 멤버 회원 가입")
    void createMember() {
        // given
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
            "CUSTOMER",
            "test@email.com",
            "test_password",
            "최배달",
            "010-1234-5678",
            "0",
            "서울시 잠실역"
        );
        MemberRole memberRole = MemberRole.builder()
            .name(MemberRoleType.CUSTOMER)
            .build();
        Member member = Member.of(memberRole, memberCreateRequest);
        when(memberRoleRepository.findById(1L)).thenReturn(Optional.ofNullable(memberRole));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        Assertions.assertThatNoException()
            .isThrownBy(() -> memberService.addMember(memberCreateRequest));

        // then
        verify(memberRoleRepository).findById(1L);
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberCaptor.capture());
        System.out.println("memberCaptor.getValue() = " + memberCaptor.getValue());
        assertAll(
            () -> assertThat(memberCaptor.getValue().getRole().getName())
                .isEqualTo(MemberRoleType.CUSTOMER),
            () -> assertThat(memberCaptor.getValue().getEmail().getEmail())
                .isEqualTo(memberCreateRequest.getEmail()),
            () -> assertThat(memberCaptor.getValue().getPassword().getPassword())
                .isEqualTo(memberCreateRequest.getPassword()),
            () -> assertThat(memberCaptor.getValue().getName().getName())
                .isEqualTo(memberCreateRequest.getName()),
            () -> assertThat(memberCaptor.getValue().getPhoneNumber().getPhoneNumber())
                .isEqualTo(memberCreateRequest.getPhoneNumber()),
            () -> assertThat(memberCaptor.getValue().getMileage().getMileage())
                .isEqualTo(memberCreateRequest.getMileage()),
            () -> assertThat(memberCaptor.getValue().getAddress().getAddress())
                .isEqualTo(memberCreateRequest.getAddress())
        );
    }

}
