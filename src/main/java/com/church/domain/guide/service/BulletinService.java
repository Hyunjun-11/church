package com.church.domain.guide.service;

import com.church.domain.guide.dto.ResponseDto;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BulletinService {


    public ResponseEntity<Message<ResponseDto>> readAll() {
        return null;
    }
}
