package com.example.hot_deal.member.domain.repository;

import com.example.hot_deal.member.domain.entity.Member;

public interface MemberRepository {

    boolean existsByEmail(String email);

    Member save(Member member);

    Member getUserByEmail(String s);

    Member getUserById(Long id);
}
