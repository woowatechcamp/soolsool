package com.woowacamp.soolsool.core.member.controller;

import com.woowacamp.soolsool.core.auth.dto.LoginUser;
import com.woowacamp.soolsool.core.member.dto.request.MemberAddRequest;
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.core.member.dto.response.MemberFindResponse;
import com.woowacamp.soolsool.core.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> addMember(
        @RequestBody @Valid final MemberAddRequest memberAddRequest
    ) {
        memberService.addMember(memberAddRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<MemberFindResponse> findMember(final @LoginUser Long userId) {
        final MemberFindResponse memberFindResponse = memberService.findMember(userId);

        return ResponseEntity.status(HttpStatus.OK).body(memberFindResponse);
    }

    @PatchMapping
    public ResponseEntity<Void> modifyMember(
        @RequestBody @Valid final MemberModifyRequest memberModifyRequest,
        final @LoginUser Long userId
    ) {
        memberService.modifyMember(userId, memberModifyRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeMember(final @LoginUser Long userId) {
        memberService.removeMember(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
