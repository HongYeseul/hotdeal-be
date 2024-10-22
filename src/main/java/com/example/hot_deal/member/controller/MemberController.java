package com.example.hot_deal.member.controller;

import com.example.hot_deal.auth.configuration.LoginUser;
import com.example.hot_deal.common.config.web.ApiV1;
import com.example.hot_deal.member.dto.RegisterRequest;
import com.example.hot_deal.member.dto.RegisterResponse;
import com.example.hot_deal.member.dto.base.BaseMemberDTO;
import com.example.hot_deal.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiV1
@Validated
@Tag(name = "member", description = "회원 API")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    @Operation(summary = "회원 가입", description = "이메일, 이름, 비밀번호를 입력하여 회원 가입을 한다.")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody final RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                memberService.register(registerRequest)
        );
    }

    @GetMapping
    @Operation(summary = "내 정보 조회", description = "로그인 한 사용자의 정보를 알 수 있다.")
    public ResponseEntity<BaseMemberDTO> findMember(@Parameter(hidden = true) @LoginUser final Long userId) {
        return ResponseEntity.ok(memberService.findMemberById(userId));
    }
}
