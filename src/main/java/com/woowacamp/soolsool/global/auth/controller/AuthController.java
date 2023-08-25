package com.woowacamp.soolsool.global.auth.controller;

import static com.woowacamp.soolsool.global.auth.code.AuthResultCode.LOGIN_SUCCESS;

import com.woowacamp.soolsool.global.auth.dto.LoginRequest;
import com.woowacamp.soolsool.global.auth.dto.LoginResponse;
import com.woowacamp.soolsool.global.auth.service.AuthService;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private static final String DEFAULT_URL = "/auth";

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
        @RequestBody final LoginRequest loginRequest
    ) {
        log.info("POST {}/login | request : {}",
            DEFAULT_URL, loginRequest);

        final LoginResponse token = authService.createToken(loginRequest);

        return ResponseEntity.ok(ApiResponse.of(LOGIN_SUCCESS, token));
    }
}
