package com.example.hot_deal.member.domain.repository;

import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.example.hot_deal.member.constants.error.MemberErrorCode.MEMBER_NOT_FOUND;


public interface MemberJpaRepository extends MemberRepository, JpaRepository<Member, Long> {

    default Member getUserByEmail(String email) {
        return findByEmail(email).orElseThrow(
                () -> new HotDealException(MEMBER_NOT_FOUND)
        );
    }

    default Member getUserById(Long id) {
        return findById(id).orElseThrow(
                () -> new HotDealException(MEMBER_NOT_FOUND)
        );
    }

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
