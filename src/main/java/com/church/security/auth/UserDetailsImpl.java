package com.church.security.auth;

import com.church.domain.members.entity.Members;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final Members member;
    private final String memberId;

    public UserDetailsImpl(Members members, String memberId) {
        this.member = members;
        this.memberId = memberId;
    }

    public Members getMember() {
        return member;
    }

    public String getMemberId() {
        return this.memberId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 단일 역할을 SimpleGrantedAuthority 리스트로 반환
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
