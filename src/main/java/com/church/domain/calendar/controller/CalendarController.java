package com.church.domain.calendar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @GetMapping("/test")
    public String index() {
        return "캘린더테스트";
    }
    
}
