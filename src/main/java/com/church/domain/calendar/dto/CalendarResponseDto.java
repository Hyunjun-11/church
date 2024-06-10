package com.church.domain.calendar.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CalendarResponseDto extends CalendarDto {
    private Long calendarId;

    // CalendarDto 객체와 id를 매개변수로 받는 생성자
    public CalendarResponseDto(CalendarDto calendarDto, Long id) {
        super(
            calendarDto.getTitle(),
            calendarDto.getContent(),
            calendarDto.getStartTime(),
            calendarDto.getEndTime());
        this.calendarId = id;
    }
}
