package com.woowacamp.soolsool.core.auth.controller;

import static com.woowacamp.soolsool.global.common.AuthResultCode.LOGIN_SUCCESS;

import com.woowacamp.soolsool.core.auth.dto.LoginRequest;
import com.woowacamp.soolsool.core.auth.dto.LoginResponse;
import com.woowacamp.soolsool.core.auth.service.AuthService;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
        @RequestBody LoginRequest loginRequest) {
        final LoginResponse token = authService.createToken(loginRequest);

        return ResponseEntity.ok(ApiResponse.of(LOGIN_SUCCESS, token));
    }
}
