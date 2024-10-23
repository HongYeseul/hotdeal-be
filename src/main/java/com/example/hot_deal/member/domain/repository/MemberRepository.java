package com.example.hot_deal.member.domain.repository;

import com.example.hot_deal.member.domain.entity.Member;

public interface MemberRepository {

    boolean existsByEmail(String email);

    Member save(Member member);

    Member getMemberByEmail(String s);

    Member getMemberById(Long id);

    void deleteAll();
}
