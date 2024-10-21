package com.example.hot_deal.user.domain.repository;

import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.example.hot_deal.common.exception.code.UserErrorCode.USER_NOT_FOUND;


public interface UserJpaRepository extends UserRepository, JpaRepository<User, Long> {

    default User getUserByEmail(String email) {
        return findByEmail(email).orElseThrow(
                () -> new HotDealException(USER_NOT_FOUND)
        );
    }

    default User getUserById(Long id) {
        return findById(id).orElseThrow(
                () -> new HotDealException(USER_NOT_FOUND)
        );
    }

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
