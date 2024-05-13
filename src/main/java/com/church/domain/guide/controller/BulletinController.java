package com.church.domain.guide.controller;


import com.church.domain.guide.dto.ResponseDto;
import com.church.domain.guide.service.BulletinService;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/bulletin")
public class BulletinController {

    private final BulletinService bulletinService;

    @GetMapping("/")
    public ResponseEntity<Message<ResponseDto>> readAll(){
        return bulletinService.readAll();

    }
}
