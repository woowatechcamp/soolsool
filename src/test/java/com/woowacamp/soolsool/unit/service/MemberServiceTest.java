package com.woowacamp.soolsool.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import com.woowacamp.soolsool.core.member.dto.request.MemberCreateRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.member.repository.MemberRoleRepository;
import com.woowacamp.soolsool.core.member.service.MemberService;
import com.woowacamp.soolsool.support.TestHelper;
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

        // when
        when(memberRoleRepository.findById(1L)).thenReturn(Optional.ofNullable(memberRole));
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        Assertions.assertThatNoException()
            .isThrownBy(() -> memberService.addMember(memberCreateRequest));

        // then
        verify(memberRoleRepository).findById(1L);
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberCaptor.capture());
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

    @Test
    @DisplayName("성공 : 멤버 조회")
    void readMeber() {
        // given
        Long userId = 1L;
        Member member = TestHelper.getMember();
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
        Member member = TestHelper.getMember();

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
        Member member = TestHelper.getMember();

        // when
        when(memberRepository.findById(userId)).thenReturn(Optional.of(member));
        Assertions.assertThatNoException()
            .isThrownBy(() -> memberService.removeMember(userId));

        // then
        verify(memberRepository).findById(userId);
        verify(memberRepository).delete(member);
    }
}
