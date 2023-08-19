package com.woowacamp.soolsool.core.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final String accessToken;

    @JsonCreator
    public LoginResponse(@JsonProperty(value = "token") final String token) {
        this.accessToken = token;
    }
}
