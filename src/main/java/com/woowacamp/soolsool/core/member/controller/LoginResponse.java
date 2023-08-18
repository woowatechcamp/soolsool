package com.woowacamp.soolsool.core.member.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;

    @JsonCreator
    public LoginResponse(final String token) {
        this.accessToken = token;
    }
}
