package com.church.domain.calendar.service;

import com.church.domain.calendar.dto.CalendarRequestDto;
import com.church.domain.calendar.dto.CalendarResponseDto;
import com.church.util.message.Message;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarService {

    public ResponseEntity<Message<CalendarResponseDto>> realAll() {
        return null;
    }

    //일정추가
    @Transactional
    public ResponseEntity<Message<CalendarResponseDto>> create(CalendarRequestDto calendarRequestDto) {
        return null;
    }

    //일정수정
    @Transactional
    public ResponseEntity<Message<CalendarResponseDto>> updateCalendar(Long id, CalendarRequestDto calendarRequestDto) {
        return null;
    }

    //일정 삭제
    @Transactional
    public ResponseEntity<Message<CalendarResponseDto>> deleteCalendar(Long id) {
        return null;
    }

}
