package com.example.hot_deal.auth.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class MemberInfo implements UserDetails {

    private Long id;
    private String email;
    private String password;

    public MemberInfo(Long id, String email) {
        this(id, email, "PASSWORD");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
