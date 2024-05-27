package com.church.domain.user.worship.bulletin.service;


import com.church.domain.user.worship.bulletin.dto.ResponseDto;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BulletinService {


    public ResponseEntity<Message<ResponseDto>> readOne() {
        return null;
    }
}
