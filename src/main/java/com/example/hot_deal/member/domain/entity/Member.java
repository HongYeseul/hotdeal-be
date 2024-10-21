package com.example.hot_deal.member.domain.entity;

import com.example.hot_deal.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseTimeEntity {

    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    public static Member create(UUID uuid, String name, String email, String encodedPassword) {
        return Member.builder()
                .uuid(uuid)
                .name(name)
                .email(email)
                .passwordHash(encodedPassword)
                .build();
    }
}
