package com.church.security.jwt;

import com.church.domain.members.entity.Members;
import com.church.security.auth.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private static final String BEARER_PREFIX = "Bearer ";
    public static final String ACCESS_KEY = "ACCESS-TOKEN";
    private static final long ACCESS_TIME = Duration.ofMinutes(30).toMillis();
    private static final long REFRESH_TIME = Duration.ofDays(7).toMillis();

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        log.info("Encoded secret key: {}", secretKey);
        try {
            byte[] bytes = Base64.getDecoder().decode(secretKey);
            log.info("Decoded bytes: {}", bytes);
            key = Keys.hmacShaKeyFor(bytes);
            log.info("Key successfully created");
        } catch (IllegalArgumentException e) {
            log.error("Failed to decode Base64 encoded key: {}", e.getMessage());
        }
    }


    public String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (JwtUtil.ACCESS_KEY.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public JwtTokenDto createAllToken(Members members) {
        return new JwtTokenDto(createToken(members));
    }

    public String createToken(Members members) {
        Date date = new Date();
        String token = Jwts.builder()
                .setSubject(members.getMemberId())
                .claim("memberID", members.getMemberId())
                .claim("email", members.getEmail())
                .claim("name", members.getName())
                .claim("role", members.getRole())
                .claim("birth", members.getBirth().toString())
                .signWith(key)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + ACCESS_TIME))
                .compact();

        return Base64.getUrlEncoder().encodeToString(token.getBytes());
    }

    public boolean validateToken(String token) {
        try {
            String jwt = new String(Base64.getUrlDecoder().decode(token));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않은 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public String getMemberInfoFromToken(String token) {
        String jwt = new String(Base64.getUrlDecoder().decode(token));
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody().getSubject();
    }

    public Authentication createAuthentication(String memberId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public void setHeaderToken(HttpServletResponse response, String accessToken) {
        response.setHeader(ACCESS_KEY, accessToken);
    }

    public long getExpirationTime(String token) {
        String jwt = new String(Base64.getUrlDecoder().decode(token));
        Date expirationDate = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(jwt).getBody().getExpiration();
        Date now = new Date();
        return expirationDate.getTime() - now.getTime();
    }
}
