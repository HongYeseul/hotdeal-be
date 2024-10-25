package com.example.hot_deal.auth.service;

import com.example.hot_deal.auth.dto.LoginRequest;
import com.example.hot_deal.auth.dto.MemberTokens;
import com.example.hot_deal.auth.provider.AuthProvider;
import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.member.domain.entity.Member;
import com.example.hot_deal.member.domain.repository.MemberRepository;
import com.example.hot_deal.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hot_deal.member.constants.error.MemberErrorCode.INCORRECT_PASSWORD;
import static com.example.hot_deal.member.constants.error.MemberErrorCode.MEMBER_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthProvider authProvider;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void cleanup() {
        memberRepository.deleteAll();
    }

    @Nested
    @DisplayName("로그인 테스트")
    class LoginTest {

        @Test
        @DisplayName("로그인 성공")
        void login() {
            Member member = memberRepository.save(MemberFixture.memberFixture());
            LoginRequest loginRequest = new LoginRequest(member.getEmail(), MemberFixture.getFixtureRawPassword());

            MemberTokens memberTokens = authService.login(loginRequest);

            assertAll(
                    () -> assertEquals(memberTokens.accessToken(), authProvider.generateToken(member).accessToken()),
                    () -> assertEquals(memberTokens.refreshToken(), authProvider.generateToken(member).refreshToken())
            );
        }

        @Test
        @DisplayName("로그인 실패: 아이디 오류")
        void login_InvalidId_Fail() {
            Member member = memberRepository.save(MemberFixture.memberFixture());
            LoginRequest loginRequest = new LoginRequest(
                    member.getEmail() + "Wrong@gmail.com",
                    MemberFixture.getFixtureRawPassword()
            );

            assertThatThrownBy(() -> authService.login(loginRequest))
                    .isInstanceOf(HotDealException.class)
                    .hasFieldOrPropertyWithValue("errorCode", MEMBER_NOT_FOUND);
        }

        @Test
        @DisplayName("로그인 실패: 비밀번호 오류")
        void login_InvalidPassword_Fail() {
            Member member = memberRepository.save(MemberFixture.memberFixture());
            LoginRequest loginRequest = new LoginRequest(
                    member.getEmail(),
                    MemberFixture.getFixtureRawPassword() + "WrongPassword"
            );

            assertThatThrownBy(() -> authService.login(loginRequest))
                    .isInstanceOf(HotDealException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INCORRECT_PASSWORD);
        }
    }
}