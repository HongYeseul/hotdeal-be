package com.example.hot_deal.user.domain.repository;

import com.example.hot_deal.user.domain.entity.User;

public interface UserRepository {

    boolean existsByEmail(String email);

    User save(User user);

    User getUserByEmail(String s);

    User getUserById(Long id);
}
