package com.example.hot_deal.auth.service;

import com.example.hot_deal.auth.dto.LoginRequest;
import com.example.hot_deal.auth.dto.MemberTokens;
import com.example.hot_deal.auth.provider.AuthProvider;
import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.member.domain.entity.Member;
import com.example.hot_deal.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.hot_deal.member.constants.error.MemberErrorCode.INCORRECT_PASSWORD;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthProvider authProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     */
    public MemberTokens login(LoginRequest loginRequest) {
        Member member = getVerifiedUser(loginRequest.loginId(), loginRequest.password());
        return authProvider.makeToken(member);
    }

    private Member getVerifiedUser(String loginId, String password) {
        Member member = memberRepository.getMemberByEmail(loginId);
        matchPassword(password, member.getPasswordHash());
        return member;
    }

    public void matchPassword(String password, String hashedPassword) {
        if (!passwordEncoder.matches(password, hashedPassword)) {
            throw new HotDealException(INCORRECT_PASSWORD);
        }
    }
}
