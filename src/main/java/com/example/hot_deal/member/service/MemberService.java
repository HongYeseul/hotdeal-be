package com.example.hot_deal.member.service;

import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.member.domain.entity.Member;
import com.example.hot_deal.member.domain.repository.MemberRepository;
import com.example.hot_deal.member.dto.RegisterRequest;
import com.example.hot_deal.member.dto.RegisterResponse;
import com.example.hot_deal.member.dto.base.BaseMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        log.info("Attempting to register member with email: {}", registerRequest.getEmail());
        assertUniqueEmail(registerRequest.getEmail());

        final Member member = new Member(
                registerRequest.getName(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getRawPassword())
        );

        memberRepository.save(member);
        log.info("Member registered successfully: {}", member.getEmail());
        return registerRequest.toResponse();
    }

    private void assertUniqueEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new HotDealException(DUPLICATE_EMAIL);
        }
    }

    /**
     * 회원 정보 조회
     */
    public BaseMemberDTO findMemberById(Long id) {
        return getMemberById(id).getBaseMemberDto();
    }

    public Member getMemberById(Long id) {
        return memberRepository.getMemberById(id);
    }
}
