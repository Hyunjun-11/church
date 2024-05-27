package com.church.domain.user.worship.bulletin.controller;

import com.church.domain.user.worship.bulletin.dto.BulletinRequestDto;
import com.church.domain.user.worship.bulletin.dto.ResponseDto;
import com.church.domain.user.worship.bulletin.service.BulletinService;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/bulletin")
public class BulletinController {

    private final BulletinService bulletinService;

    @GetMapping("/")
    public ResponseEntity<Message<ResponseDto>> readOne(){
        return bulletinService.readOne();
    }
    //주보 게시판 등록
    @PostMapping("/create")
    public ResponseEntity<Message<ResponseDto>> create(@RequestBody BulletinRequestDto requestDto){
        return null;
    }

    @PostMapping(value = "/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Message<ResponseDto>> uploadImage(@ModelAttribute BulletinRequestDto requestDto){
        return null;
    }


}
