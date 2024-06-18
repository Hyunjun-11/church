package com.church.security.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    boolean existsByMemberId(String memberId);
}