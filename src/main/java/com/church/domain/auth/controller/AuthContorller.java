package com.church.domain.auth.controller;


import com.church.domain.auth.service.AuthService;
import com.church.domain.members.dto.response.MemberResponseDto;
import com.church.util.message.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthContorller {

    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<Message<MemberResponseDto>> auth(HttpServletRequest request) {
        return authService.auth(request);

    }
}
