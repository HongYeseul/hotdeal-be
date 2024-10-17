package com.example.hot_deal.user.service;

import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.user.domain.entity.User;
import com.example.hot_deal.user.domain.repository.UserRepository;
import com.example.hot_deal.user.dto.RegisterRequest;
import com.example.hot_deal.user.dto.RegisterResponse;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.hot_deal.common.exception.code.UserErrorCode.DUPLICATE_EMAIL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private static FixtureMonkey fixtureMonkey;

    @BeforeAll
    static void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
    }

    @Test
    void 회원가입_성공() {
        // Given
        RegisterRequest request = fixtureMonkey.giveMeOne(RegisterRequest.class);
        String encodedPassword = "encodedPassword";

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getRawPassword())).thenReturn(encodedPassword);

        // When
        RegisterResponse response = userService.register(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(request.getEmail());
        assertThat(response.getName()).isEqualTo(request.getName());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void 회원가입_실패_이메일_중복() {
        // Given
        RegisterRequest request = fixtureMonkey.giveMeOne(RegisterRequest.class);

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(HotDealException.class)
                .hasFieldOrPropertyWithValue("errorCode", DUPLICATE_EMAIL);

        verify(userRepository, never()).save(any(User.class));
    }
}