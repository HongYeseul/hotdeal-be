package com.example.hot_deal.member.domain.entity;

import com.example.hot_deal.common.domain.BaseTimeEntity;
import com.example.hot_deal.member.dto.base.BaseMemberDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * 멤버 회원 가입
     */
    public Member(String name, String email, String encodedPassword) {
        this(null, name, email, encodedPassword);
    }

    public static Member makeAuthMember(Long id, String email) {
        return new Member(id, "AUTH", email, "PASSWORD");
    }

    public BaseMemberDTO getBaseMemberDto() {
        return new BaseMemberDTO(this.getEmail(), this.getPasswordHash());
    }
}
