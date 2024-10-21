package com.example.hot_deal.member.service;

import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.member.domain.entity.Member;
import com.example.hot_deal.member.domain.repository.MemberRepository;
import com.example.hot_deal.member.dto.RegisterRequest;
import com.example.hot_deal.member.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.example.hot_deal.member.constants.error.MemberErrorCode.DUPLICATE_EMAIL;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    public RegisterResponse register(RegisterRequest registerRequest) {
        log.info("Attempting to register member with email: {}", registerRequest.getEmail());
        if (memberRepository.existsByEmail(registerRequest.getEmail())) {
            log.warn("Registration failed: Email already exists - {}", registerRequest.getEmail());
            throw new HotDealException(DUPLICATE_EMAIL);
        }

        Member member = Member.create(
                UUID.randomUUID(),
                registerRequest.getName(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getRawPassword())
        );

        memberRepository.save(member);
        log.info("Member registered successfully: {}", member.getEmail());
        return registerRequest.toResponse();
    }
}
