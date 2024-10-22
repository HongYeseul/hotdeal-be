package com.example.hot_deal.member.domain.repository;

import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.member.domain.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import static com.example.hot_deal.member.constants.error.MemberErrorCode.MEMBER_NOT_FOUND;

public class MemberFakeRepository implements MemberRepository {

    private final AtomicLong idCounter = new AtomicLong(1);

    private final List<Member> members;

    public MemberFakeRepository() {
        this.members = new ArrayList<>();
    }

    public MemberFakeRepository(List<Member> members) {
        this.members = new ArrayList<>(members);
        idCounter.set(1 + members.size());
    }

    @Override
    public boolean existsByEmail(String email) {
        return members.stream().anyMatch(member -> member.getEmail().equals(email));
    }

    @Override
    public Member save(Member entity) {
        Member newMember = new Member(
                getOrGenerateId(entity),
                entity.getName(),
                entity.getEmail(),
                entity.getPasswordHash()
        );
        members.removeIf(member -> Objects.equals(member.getEmail(), entity.getEmail()));
        members.add(newMember);
        return  newMember;
    }

    @Override
    public Member getMemberByEmail(String email) {
        return members.stream()
                .filter(member -> Objects.equals(member.getEmail(), email))
                .findFirst()
                .orElseThrow(() -> new HotDealException(MEMBER_NOT_FOUND));
    }

    @Override
    public Member getMemberById(Long id) {
        return members.stream()
                .filter(member -> Objects.equals(member.getId(), id))
                .findFirst()
                .orElseThrow(() -> new HotDealException(MEMBER_NOT_FOUND));
    }

    private long getOrGenerateId(Member entity) {
        if (existsById(entity.getId())) {
            return entity.getId();
        }
        return idCounter.getAndIncrement();
    }

    private boolean existsById(Long id) {
        return members.stream().anyMatch(member -> Objects.equals(member.getId(), id));
    }
}
