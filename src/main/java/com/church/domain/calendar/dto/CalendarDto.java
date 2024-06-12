package com.church.domain.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto {
    private String title;
    private String content;
    private String startTime;
    private String endTime;
    private String color;


}
