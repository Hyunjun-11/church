package com.church.domain.members.controller;


import com.church.domain.members.dto.request.MemberRequestDto;
import com.church.domain.members.dto.response.MemberResponseDto;
import com.church.domain.members.service.MemberService;
import com.church.domain.members.dto.request.SignInRequestDto;
import com.church.util.message.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    //회원 전체조회
//    @GetMapping("/readAll")
//    public ResponseEntity<Message<List<MemberResponseDto>>> readAll() {
//        return memberService.readAll();
//    }
    @GetMapping("/readAAll")
    public String readAll() {
        return "전체조회";
    }

    //회원 단일조회
    @GetMapping("/{id}")
    public ResponseEntity<Message<MemberResponseDto>> findById(@PathVariable Long id) {
        return memberService.readOne(id);
    }

    //회원가입
    @PostMapping("/signUp")
    public ResponseEntity<Message<MemberResponseDto>> create(@Valid @RequestBody MemberRequestDto requestDto) {
        return memberService.signUp(requestDto);
    }
    //로그인
    @PostMapping("/signIn")
    public ResponseEntity<Message<MemberResponseDto>> signIn(@Valid @RequestBody SignInRequestDto requestDto) {
        return memberService.signIn(requestDto);
    }
    @DeleteMapping("/logOut")
    public ResponseEntity<Message<String>> logOut() {
        return null;
    }
}
