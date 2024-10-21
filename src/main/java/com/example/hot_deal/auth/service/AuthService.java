package com.example.hot_deal.auth.service;

import com.example.hot_deal.auth.dto.LoginRequest;
import com.example.hot_deal.auth.dto.LoginResponse;
import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.user.domain.entity.User;
import com.example.hot_deal.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.hot_deal.common.exception.code.UserErrorCode.INCORRECT_PASSWORD;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthProvider authProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     */
    public LoginResponse login(LoginRequest loginRequest) {
        User user = getVerifiedUser(loginRequest.loginId(), loginRequest.password());
        return authProvider.makeToken(user);
    }

    private User getVerifiedUser(String loginId, String password) {
        User user = userRepository.getUserByEmail(loginId);
        matchPassword(password, user.getPasswordHash());
        return user;
    }

    public void matchPassword(String password, String hashedPassword) {
        if (!passwordEncoder.matches(password, hashedPassword)) {
            throw new HotDealException(INCORRECT_PASSWORD);
        }
    }
}
