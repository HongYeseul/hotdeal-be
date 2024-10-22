package com.example.hot_deal.auth.configuration;

import com.example.hot_deal.member.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class CustomUserDetails implements UserDetails {

    Member member;
    private Map<String, Object> attributes;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public static CustomUserDetails from(Member member) {
        return new CustomUserDetails(member);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return member.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }
}

