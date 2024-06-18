package com.church.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository tokenRepository;


    public boolean existsRefreshToken(String memberId) {
        return tokenRepository.existsByMemberId(memberId);
    }

    // 추가적으로 토큰을 저장하는 메서드 등도 작성할 수 있습니다.
    public void saveRefreshToken(String memberId, String token) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setMemberId(memberId);
        refreshToken.setToken(token);
        tokenRepository.save(refreshToken);
    }
}