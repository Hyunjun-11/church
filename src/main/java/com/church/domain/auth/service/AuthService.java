package com.church.domain.auth.service;

import com.church.domain.members.dto.response.SignInResponseDto;
import com.church.domain.members.entity.Members;
import com.church.domain.members.repository.MemberRepository;
import com.church.domain.members.service.MemberService;
import com.church.security.jwt.JwtUtil;
import com.church.util.message.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.church.security.jwt.JwtUtil.ACCESS_KEY;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final MemberRepository membersRepository;
    
    
    public ResponseEntity<Message<SignInResponseDto>> auth(HttpServletRequest request) {
        String accessToken = jwtUtil.resolveToken(request, ACCESS_KEY);
        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            String memberId = jwtUtil.getMemberInfoFromToken(accessToken);
            Members member = membersRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
            SignInResponseDto responseDto = new SignInResponseDto(member);
            return new ResponseEntity<>(new Message<>("사용자 정보조회 성공", responseDto), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }
}
