package com.example.hot_deal.auth.controller;

import com.example.hot_deal.auth.dto.LoginRequest;
import com.example.hot_deal.auth.dto.MemberTokens;
import com.example.hot_deal.auth.manager.CredentialManager;
import com.example.hot_deal.auth.service.AuthService;
import com.example.hot_deal.common.config.web.ApiV1;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.hot_deal.auth.constants.JwtConstants.AUTHORIZATION_HEADER;
import static com.example.hot_deal.auth.constants.JwtConstants.BEARER_TOKEN_PREFIX;

@ApiV1
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CredentialManager credentialManager;

    /**
     * 로그인
     */
//    @PostMapping("/login")
//    public ResponseEntity<MemberTokens> login(
//            @Valid @RequestBody LoginRequest loginRequest,
//            HttpServletResponse httpServletResponse
//    ) {
//        MemberTokens memberTokens = authService.login(loginRequest);
//        credentialManager.setCredential(memberTokens, httpServletResponse);
//        return ResponseEntity.ok(memberTokens);
//    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일, 비밀번호를 입력하여 로그인을 한다.")
    public ResponseEntity<Void> login(@Valid @RequestBody final LoginRequest loginRequest, HttpServletResponse response) {
        MemberTokens tokens = authService.login(loginRequest);
        response.addCookie(tokens.refreshToken());
        response.setHeader(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + tokens.accessToken());
        return ResponseEntity.ok().build();
    }
}
