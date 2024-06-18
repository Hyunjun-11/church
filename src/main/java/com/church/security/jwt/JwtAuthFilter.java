package com.church.security.jwt;

import com.church.domain.members.entity.Members;
import com.church.domain.members.repository.MemberRepository;
import com.church.util.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.church.security.jwt.JwtUtil.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final MemberRepository membersRepository;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String access_token = jwtUtil.resolveToken(request, ACCESS_KEY);
        String refresh_token = jwtUtil.resolveToken(request, REFRESH_KEY);

        if (access_token != null) {
            if (jwtUtil.validateToken(access_token)) {
                setAuthentication(jwtUtil.getMemberInfoFromToken(access_token));
            } else if (refresh_token != null && jwtUtil.existsRefreshToken(jwtUtil.getMemberInfoFromToken(refresh_token))) {
                String memberId = jwtUtil.getMemberInfoFromToken(refresh_token);

                Members members = membersRepository.findByMemberId(memberId).orElseThrow(
                        () -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다. 아이디 : " + memberId));

                String newAccessToken = jwtUtil.createToken(members, "Access");

                // HttpOnly 쿠키 설정
                String pureNewAccessToken = newAccessToken.replace("Bearer ", "").trim();
                Cookie accessTokenCookie = new Cookie("ACCESS-TOKEN", pureNewAccessToken);
                accessTokenCookie.setHttpOnly(true);
                accessTokenCookie.setSecure(false); // HTTPS를 사용하는 경우 true로 설정
                accessTokenCookie.setPath("/");
                accessTokenCookie.setMaxAge(60*60);

                response.addCookie(accessTokenCookie);

                // 새로 발급된 액세스 토큰으로 인증 설정
                setAuthentication(jwtUtil.getMemberInfoFromToken(pureNewAccessToken));
            } else {
                jwtExceptionHandler(response, "Invalid Refresh Token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String memberId) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(memberId);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new Message<>(msg, null));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
