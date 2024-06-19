package com.church.domain.members.service;


import com.church.domain.members.dto.request.MemberRequestDto;
import com.church.domain.members.dto.request.SignInRequestDto;
import com.church.domain.members.dto.response.MemberResponseDto;
import com.church.domain.members.dto.response.SignInResponseDto;
import com.church.domain.members.entity.Members;
import com.church.domain.members.entity.ROLE;
import com.church.domain.members.repository.MemberRepository;
import com.church.security.jwt.JwtTokenDto;
import com.church.security.jwt.JwtUtil;
import com.church.security.jwt.TokenService;
import com.church.util.message.Message;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;


    //멤버 전체조회
    public ResponseEntity<Message<List<MemberResponseDto>>> readAll() {
        List<Members> member= memberRepository.findAll();
        List<MemberResponseDto> responseList =new ArrayList<>();
        for(Members m : member){
            responseList.add(new MemberResponseDto(m));
        }
        return new ResponseEntity<>(new Message<>("멤버 전체 조회성공",responseList), HttpStatus.OK);
    }


    //멤버 단일조회
    public ResponseEntity<Message<MemberResponseDto>> readOne(Long id) {

       Members member= findById(id);

       MemberResponseDto memberResponseDto = new MemberResponseDto(member);

       return new ResponseEntity<>(new Message<>("멤버 단일 조회 성공",memberResponseDto),HttpStatus.OK);
    }

    //회원가입
    @Transactional
    public ResponseEntity<Message<MemberResponseDto>> signUp(MemberRequestDto memberRequestDto) {
        System.out.println("회원가입");


        String memberId = memberRequestDto.getMemberId();

        Optional<Members> duplicateMember = findByMemberId(memberId);

        if(!duplicateMember.isEmpty()) {
            throw new EntityExistsException("이미 존재하는 아이디입니다.");
        }
        Members member = createMember(memberRequestDto);
        memberRepository.save(member);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);

        return new ResponseEntity<>(new Message<>("회원 가입 성공",memberResponseDto),HttpStatus.OK);

    }


    //로그인
//    @Transactional
//    public ResponseEntity<Message<MemberResponseDto>> signIn(SignInRequestDto requestDto, HttpServletResponse httpServletResponse) {
//        String memberId = requestDto.getMemberId();
//        Optional<Members> optionalMember = findByMemberId(memberId);
//
//        if(optionalMember.isEmpty() || !passwordEncoder.matches(requestDto.getPassword(),optionalMember.get().getPassword())) {
//            throw new UsernameNotFoundException("회원 정보가 일치하지 않습니다.");
//        }
//        MemberResponseDto memberResponseDto = new MemberResponseDto(optionalMember.get());
//
//        JwtTokenDto tokenDto = jwtUtil.createAllToken(optionalMember.get());
//       jwtUtil.setHeaderToken(httpServletResponse,tokenDto.getAccessToken());
//        return new ResponseEntity<>(new Message<>("로그인 성공",memberResponseDto),HttpStatus.OK);
//    }
    //httpOnly
    @Transactional
    public ResponseEntity<Message<SignInResponseDto>> signIn(SignInRequestDto requestDto, HttpServletResponse httpServletResponse) {
        String memberId = requestDto.getMemberId();
        Optional<Members> optionalMember = memberRepository.findByMemberId(memberId);

        if (optionalMember.isEmpty() || !passwordEncoder.matches(requestDto.getPassword(), optionalMember.get().getPassword())) {
            throw new UsernameNotFoundException("회원 정보가 일치하지 않습니다.");
        }

        SignInResponseDto responseDto = new SignInResponseDto(optionalMember.get());

        // JWT 토큰 생성
        JwtTokenDto token = jwtUtil.createAllToken(optionalMember.get());

        // 리프레시 토큰을 데이터베이스에 저장
        String pureRefreshToken = token.getRefreshToken().replace("Bearer ", "");
        tokenService.saveRefreshToken(memberId, pureRefreshToken);

        // HttpOnly 쿠키 설정 (Bearer 접두사를 제거한 순수 토큰 저장)
        String pureAccessToken = token.getAccessToken().replace("Bearer ", "");
        Cookie accessTokenCookie = new Cookie("ACCESS-TOKEN", pureAccessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true); // HTTPS를 사용하는 경우 true로 설정
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(3 * 24 * 60 * 60); // 3일

        Cookie refreshTokenCookie = new Cookie("REFRESH-TOKEN", pureRefreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS를 사용하는 경우 true로 설정
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(3 * 24 * 60 * 60); // 7일

        httpServletResponse.addCookie(accessTokenCookie);
        httpServletResponse.addCookie(refreshTokenCookie);

        return new ResponseEntity<>(new Message<>("로그인 성공", responseDto), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message<String>> signOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("ACCESS-TOKEN", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true); // HTTPS를 사용하는 경우 true로 설정
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0); // 쿠키 삭제

        Cookie refreshTokenCookie = new Cookie("REFRESH-TOKEN", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS를 사용하는 경우 true로 설정
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // 쿠키 삭제

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(new Message<>("로그아웃 성공", null));
    }

    private Members findById(Long id){
        return memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("회원 정보를 찾을 수 없습니다."));
    }
    private Optional<Members> findByMemberId(String memberId){
        return memberRepository.findByMemberId(memberId);
    }
    private Members createMember(MemberRequestDto requestDTO) {
        ROLE role = requestDTO.getRole() != null ? requestDTO.getRole() : ROLE.USER;
        return Members.builder()
                .memberId(requestDTO.getMemberId())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .birth(requestDTO.getBirth())
                .role(role)
                .build();

    }

}
