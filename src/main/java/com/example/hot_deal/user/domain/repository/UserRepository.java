package com.example.hot_deal.user.domain.repository;

import com.example.hot_deal.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
