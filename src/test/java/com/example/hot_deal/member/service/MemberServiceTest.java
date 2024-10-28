package com.example.hot_deal.member.service;

import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.config.TestConfig;
import com.example.hot_deal.member.domain.entity.Member;
import com.example.hot_deal.member.domain.repository.MemberRepository;
import com.example.hot_deal.member.dto.RegisterRequest;
import com.example.hot_deal.member.dto.RegisterResponse;
import com.example.hot_deal.fixture.MemberFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hot_deal.member.constants.error.MemberErrorCode.DUPLICATE_EMAIL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@Transactional
@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void cleanup() {
        memberRepository.deleteAll();
    }

    @Nested
    @DisplayName("회원 가입 테스트")
    class RegisterTest {

        @Test
        @DisplayName("회원 가입 성공: 멤버 생성")
        void register() {
            // Given
            Member member = MemberFixture.createMember();
            RegisterRequest request = new RegisterRequest(member.getEmail(), member.getName(), member.getPasswordHash());

            // When
            RegisterResponse response = memberService.register(request);

            // Then
            assertAll(
                    () -> assertThat(response).isNotNull(),
                    () -> assertThat(response.getEmail()).isEqualTo(member.getEmail())
            );
        }

        @Test
        @DisplayName("회원 가입 실패: 이메일 중복")
        void register_emailDuplicate_Fail() {
            // Given
            Member member = MemberFixture.createMember();
            Member existMember = memberRepository.save(member);
            RegisterRequest request = new RegisterRequest(existMember.getEmail(), existMember.getName(), existMember.getPasswordHash());

            // When & Then
            assertThatThrownBy(() -> memberService.register(request))
                    .isInstanceOf(HotDealException.class)
                    .hasFieldOrPropertyWithValue("errorCode", DUPLICATE_EMAIL);
        }
    }
}