package com.church.domain.calendar.controller;

import com.church.domain.calendar.dto.CalendarRequestDto;
import com.church.domain.calendar.dto.CalendarResponseDto;
import com.church.domain.calendar.service.CalendarService;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/test")
    public String index() {
        return "캘린더테스트";
    }

    @GetMapping("/")
    public ResponseEntity<Message<CalendarResponseDto>> realAll() {
        return calendarService.realAll();
    }
    @PostMapping("/")
    public ResponseEntity<Message<CalendarResponseDto>> saveCalendar(@RequestBody CalendarRequestDto calendarRequestDto) {
        return calendarService.saveCalendar(calendarRequestDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Message<CalendarResponseDto>> updateCalendar(@PathVariable Long id, @RequestBody CalendarRequestDto calendarRequestDto) {
        return calendarService.updateCalendar(id,calendarRequestDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Message<CalendarResponseDto>> deleteCalendar(@PathVariable Long id) {
        return calendarService.deleteCalendar(id);

    }

}
