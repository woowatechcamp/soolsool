package com.woowacamp.soolsool.core.member.controller;

import com.woowacamp.soolsool.core.member.code.MemberResultCode;
import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberMileageChargeRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import com.woowacamp.soolsool.core.member.service.MemberService;
import com.woowacamp.soolsool.global.auth.dto.LoginUser;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.common.ApiResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @NoAuth
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addMember(
        final HttpServletRequest httpServletRequest,
        @RequestBody @Valid final MemberAddRequest memberAddRequest
    ) {
        log.info("{} {} | request : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberAddRequest);

        memberService.addMember(memberAddRequest);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.from(MemberResultCode.MEMBER_CREATE_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<MemberFindResponse>> findMemberDetails(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId
    ) {
        log.info("{} {} | memberId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId);

        final MemberFindResponse memberFindResponse = memberService.findMember(memberId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.of(MemberResultCode.MEMBER_FIND_SUCCESS, memberFindResponse));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> modifyMember(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId,
        @RequestBody @Valid final MemberModifyRequest memberModifyRequest
    ) {
        log.info("{} {} | memberId : {} | request : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(),
            memberId, memberModifyRequest);

        memberService.modifyMember(memberId, memberModifyRequest);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.from(MemberResultCode.MEMBER_MODIFY_SUCCESS));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> removeMember(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId
    ) {
        log.info("{} {} | memberId : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), memberId);

        memberService.removeMember(memberId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse.from(MemberResultCode.MEMBER_DELETE_SUCCESS));
    }

    @PatchMapping("/mileage")
    public ResponseEntity<ApiResponse<Void>> addMemberMileage(
        final HttpServletRequest httpServletRequest,
        @LoginUser final Long memberId,
        @RequestBody @Valid final MemberMileageChargeRequest memberMileageChargeRequest
    ) {
        log.info("{} {} | memberId : {} | request : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(),
            memberId, memberMileageChargeRequest);

        memberService.addMemberMileage(memberId, memberMileageChargeRequest);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.from(MemberResultCode.MEMBER_MILEAGE_CHARGE_SUCCESS));
    }
}
