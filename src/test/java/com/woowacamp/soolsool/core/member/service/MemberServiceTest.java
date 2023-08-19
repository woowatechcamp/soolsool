package com.woowacamp.soolsool.core.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.member.repository.MemberRoleRepository;
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
class MemberServiceTest {

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
        MemberAddRequest memberAddRequest = new MemberAddRequest(
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
        Member member = memberAddRequest.toMember(memberRole);

        // when
        when(memberRoleRepository.findById(1L)).thenReturn(Optional.ofNullable(memberRole));
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        Assertions.assertThatNoException()
            .isThrownBy(() -> memberService.addMember(memberAddRequest));

        // then
        verify(memberRoleRepository).findById(1L);
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberCaptor.capture());
        assertAll(
            () -> assertThat(memberCaptor.getValue().getRole().getName())
                .isEqualTo(MemberRoleType.CUSTOMER),
            () -> assertThat(memberCaptor.getValue().getEmail().getEmail())
                .isEqualTo(memberAddRequest.getEmail()),
            () -> assertThat(memberCaptor.getValue().getPassword().getPassword())
                .isEqualTo(memberAddRequest.getPassword()),
            () -> assertThat(memberCaptor.getValue().getName().getName())
                .isEqualTo(memberAddRequest.getName()),
            () -> assertThat(memberCaptor.getValue().getPhoneNumber().getPhoneNumber())
                .isEqualTo(memberAddRequest.getPhoneNumber()),
            () -> assertThat(memberCaptor.getValue().getMileage().getMileage())
                .isEqualTo(memberAddRequest.getMileage()),
            () -> assertThat(memberCaptor.getValue().getAddress().getAddress())
                .isEqualTo(memberAddRequest.getAddress())
        );
    }

    @Test
    @DisplayName("성공 : 멤버 조회")
    void readMember() {
        // given
        Long userId = 1L;
        Member member = Member.builder()
            .role(MemberRole.builder()
                .name(MemberRoleType.CUSTOMER)
                .build())
            .email("test@email.com")
            .password("test_password")
            .name("최배달")
            .phoneNumber("010-1234-5678")
            .mileage("0")
            .address("서울시 잠실역")
            .build();
        MemberFindResponse memberFindResponseExpected = MemberFindResponse.of(member);

        // when
        when(memberRepository.findById(userId)).thenReturn(Optional.of(member));
        MemberFindResponse memberFindResponse = memberService.findMember(userId);

        // then
        assertAll(
            () -> assertThat(memberFindResponse.getRoleName())
                .isEqualTo(memberFindResponseExpected.getRoleName()),
            () -> assertThat(memberFindResponse.getName())
                .isEqualTo(memberFindResponseExpected.getName()),
            () -> assertThat(memberFindResponse.getMileage())
                .isEqualTo(memberFindResponseExpected.getMileage()),
            () -> assertThat(memberFindResponse.getEmail())
                .isEqualTo(memberFindResponseExpected.getEmail()),
            () -> assertThat(memberFindResponse.getAddress())
                .isEqualTo(memberFindResponseExpected.getAddress())
        );
    }

    @Test
    @DisplayName("성공 : 회원 정보 수정")
    void modifyMember() {
        // given
        Long userId = 1L;
        MemberModifyRequest memberModifyRequest = new MemberModifyRequest(
            "new_password",
            "new_name",
            "new_address"
        );
        Member member = Member.builder()
            .role(MemberRole.builder()
                .name(MemberRoleType.CUSTOMER)
                .build())
            .email("test@email.com")
            .password("test_password")
            .name("최배달")
            .phoneNumber("010-1234-5678")
            .mileage("0")
            .address("서울시 잠실역")
            .build();

        // when
        when(memberRepository.findById(userId)).thenReturn(Optional.ofNullable(member));
        member.update(memberModifyRequest);
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        Assertions.assertThatNoException()
            .isThrownBy(() -> memberService.modifyMember(userId, memberModifyRequest));

        // then
        verify(memberRepository).findById(1L);
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberCaptor.capture());
        assertAll(
            () -> assertThat(member.getPassword().getPassword())
                .isEqualTo(memberModifyRequest.getPassword()),
            () -> assertThat(member.getName().getName())
                .isEqualTo(memberModifyRequest.getName()),
            () -> assertThat(member.getAddress().getAddress())
                .isEqualTo(memberModifyRequest.getAddress())
        );
    }

    @Test
    @DisplayName("성공 : 회원 정보 삭제")
    void removeMember() {
        // given
        Long userId = 1L;
        Member member = Member.builder()
            .role(MemberRole.builder()
                .name(MemberRoleType.CUSTOMER)
                .build())
            .email("test@email.com")
            .password("test_password")
            .name("최배달")
            .phoneNumber("010-1234-5678")
            .mileage("0")
            .address("서울시 잠실역")
            .build();

        // when
        when(memberRepository.findById(userId)).thenReturn(Optional.of(member));
        Assertions.assertThatNoException()
            .isThrownBy(() -> memberService.removeMember(userId));

        // then
        verify(memberRepository).findById(userId);
        verify(memberRepository).delete(member);
    }
}