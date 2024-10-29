package com.example.hot_deal.auth.controller;

import com.example.hot_deal.auth.dto.LoginRequest;
import com.example.hot_deal.auth.dto.MemberTokens;
import com.example.hot_deal.auth.provider.AuthProvider;
import com.example.hot_deal.auth.service.AuthService;
import com.example.hot_deal.common.config.web.ApiV1;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ApiV1
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthProvider authProvider;

    /**
     * 로그인
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일, 비밀번호를 입력하여 로그인을 한다.")
    public ResponseEntity<Void> login(@Valid @RequestBody final LoginRequest loginRequest, HttpServletResponse response) {
        MemberTokens memberTokens = authService.login(loginRequest);
        authProvider.setCredentials(memberTokens, response);
        return ResponseEntity.ok().build();
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.ok().build();
    }
}
