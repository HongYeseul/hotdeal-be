package com.example.hot_deal.user.controller;

import com.example.hot_deal.common.config.web.ApiV1;
import com.example.hot_deal.user.dto.RegisterRequest;
import com.example.hot_deal.user.dto.RegisterResponse;
import com.example.hot_deal.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiV1
@Validated
@Tag(name = "user", description = "회원 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "회원 가입", description = "이메일, 이름, 비밀번호를 입력하여 회원 가입을 한다.")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody final RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userService.register(registerRequest)
        );
    }
}
