package com.church.domain.members.controller;


import com.church.domain.members.dto.request.MemberRequestDto;
import com.church.domain.members.dto.request.SignInRequestDto;
import com.church.domain.members.dto.response.MemberResponseDto;
import com.church.domain.members.dto.response.SignInResponseDto;
import com.church.domain.members.service.MemberService;
import com.church.util.message.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
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
    public ResponseEntity<Message<SignInResponseDto>> signIn(@Valid @RequestBody SignInRequestDto requestDto, HttpServletResponse httpServletResponse) {
        return memberService.signIn(requestDto,httpServletResponse);
    }
    //로그아웃
    @DeleteMapping("/signOut")
    public ResponseEntity<Message<String>> signOut(HttpServletRequest request, HttpServletResponse response) {
        return memberService.signOut(request,response);
    }

}
