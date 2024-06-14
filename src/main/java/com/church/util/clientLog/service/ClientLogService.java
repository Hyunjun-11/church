package com.church.util.clientLog.service;

import com.church.util.clientLog.dto.ClientLogDtoResponseDto;
import com.church.util.clientLog.repository.ClientLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientLogService {

    private final ClientLogRepository clientLogRepository;


    //로그 전체조회
    @Transactional
    public ResponseEntity<ClientLogDtoResponseDto> readAll(){

        return null;
    }

    //로그 아이디(userId) 조회
    @Transactional
    public ResponseEntity<ClientLogDtoResponseDto> readById(){

        return null;
    }
}
