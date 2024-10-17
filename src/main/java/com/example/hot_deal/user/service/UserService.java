package com.example.hot_deal.user.service;

import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.user.domain.entity.User;
import com.example.hot_deal.user.domain.repository.UserRepository;
import com.example.hot_deal.user.dto.RegisterRequest;
import com.example.hot_deal.user.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.example.hot_deal.common.exception.code.UserErrorCode.DUPLICATE_EMAIL;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    public RegisterResponse register(RegisterRequest registerRequest) {
        log.info("Attempting to register user with email: {}", registerRequest.getEmail());
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            log.warn("Registration failed: Email already exists - {}", registerRequest.getEmail());
            throw new HotDealException(DUPLICATE_EMAIL);
        }

        User user = User.create(
                UUID.randomUUID(),
                registerRequest.getName(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getRawPassword())
        );

        userRepository.save(user);
        log.info("User registered successfully: {}", user.getEmail());
        return registerRequest.toResponse();
    }
}
