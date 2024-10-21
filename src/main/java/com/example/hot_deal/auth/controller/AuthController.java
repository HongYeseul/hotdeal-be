package com.example.hot_deal.auth.controller;

import com.example.hot_deal.auth.dto.LoginRequest;
import com.example.hot_deal.auth.dto.MemberTokens;
import com.example.hot_deal.auth.manager.CredentialManager;
import com.example.hot_deal.auth.service.AuthService;
import com.example.hot_deal.common.config.web.ApiV1;
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
    private final CredentialManager credentialManager;

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<MemberTokens> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse httpServletResponse
    ) {
        MemberTokens memberTokens = authService.login(loginRequest);
        credentialManager.setCredential(memberTokens, httpServletResponse);
        return ResponseEntity.ok(memberTokens);
    }
}
