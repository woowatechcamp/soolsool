package com.woowacamp.soolsool.core.auth.service;

import com.woowacamp.soolsool.core.auth.util.TokenProvider;
import com.woowacamp.soolsool.core.member.controller.LoginResponse;
import com.woowacamp.soolsool.core.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;

    public LoginResponse createToken(final Member member) {
        return new LoginResponse(tokenProvider.createToken(member));
    }
}
