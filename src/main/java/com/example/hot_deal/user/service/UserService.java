package com.example.hot_deal.user.service;

import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.common.exception.code.ErrorCode;
import com.example.hot_deal.user.domain.entity.User;
import com.example.hot_deal.user.domain.repository.UserRepository;
import com.example.hot_deal.user.dto.RegisterRequest;
import com.example.hot_deal.user.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.example.hot_deal.common.exception.code.UserErrorCode.DUPLICATE_LOGIN_ID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * 회원 가입
     */
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new HotDealException(DUPLICATE_LOGIN_ID);
        }

        User user = User.createWithEncodedPassword(
                UUID.randomUUID(),
                registerRequest.getName(),
                registerRequest.getEmail(),
                encoder.encode(registerRequest.getRawPassword())
        );

        userRepository.save(user);
        return registerRequest.toResponse();
    }
}
