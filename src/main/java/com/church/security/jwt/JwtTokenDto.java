package com.church.security.jwt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class JwtTokenDto {

    private String accessToken;
    private String refreshToken;
}
