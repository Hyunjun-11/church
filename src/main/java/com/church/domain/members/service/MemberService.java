package com.church.domain.members.service;


import com.church.domain.members.dto.request.MemberRequestDto;
import com.church.domain.members.dto.request.SignInRequestDto;
import com.church.domain.members.dto.response.MemberResponseDto;
import com.church.domain.members.entity.Members;
import com.church.domain.members.repository.MemberRepository;
import com.church.util.message.Message;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
    @Transactional
    public ResponseEntity<Message<MemberResponseDto>> signIn(SignInRequestDto requestDto) {
        String memberId = requestDto.getMemberId();
        Optional<Members> optionalMember = findByMemberId(memberId);

        if(optionalMember.isEmpty() || passwordEncoder.matches( requestDto.getPassword(),optionalMember.get().getPassword())) {
            throw new UsernameNotFoundException("회원 정보가 일치하지 않습니다.");
        }
        MemberResponseDto memberResponseDto = new MemberResponseDto(optionalMember.get());

        //토큰은 추후에 만들기

        return new ResponseEntity<>(new Message<>("로그인 성공",memberResponseDto),HttpStatus.OK);
    }

    private Members findById(Long id){
        return memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("회원 정보를 찾을 수 없습니다."));
    }
    private Optional<Members> findByMemberId(String memberId){
        return memberRepository.findByMemberId(memberId);
    }
    private Members createMember(MemberRequestDto requestDTO) {
        System.out.println(requestDTO.toString());
        return Members.builder()
                .memberId(requestDTO.getMemberId())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .birth(requestDTO.getBirth())
                .role(requestDTO.getRole())
                .build();
    }

}
