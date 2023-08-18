package com.woowacamp.soolsool.core.auth.controller;

import static com.woowacamp.soolsool.global.common.AuthResultCode.LOGIN_SUCCESS;

import com.woowacamp.soolsool.core.auth.dto.request.LoginRequest;
import com.woowacamp.soolsool.core.member.controller.LoginResponse;
import com.woowacamp.soolsool.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
        @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = null;
        return ResponseEntity.ok(ApiResponse.of(LOGIN_SUCCESS, loginResponse));
    }
}
