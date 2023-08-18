package com.woowacamp.soolsool.core.auth.controller;

import static com.woowacamp.soolsool.global.common.AuthResultCode.LOGIN_SUCCESS;

import com.woowacamp.soolsool.core.auth.service.AuthService;
import com.woowacamp.soolsool.core.member.controller.LoginResponse;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.dto.request.LoginRequest;
import com.woowacamp.soolsool.core.member.service.MemberService;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
        @RequestBody LoginRequest loginRequest) {
        final Member member = memberService.matchMember(loginRequest);

        return ResponseEntity.ok(ApiResponse.of(LOGIN_SUCCESS, authService.createToken(member)));
    }

}
