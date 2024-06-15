package com.church.security.jwt;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class JwtTokenDto {

    private String accessToken;
    private String refreshToken;
}
