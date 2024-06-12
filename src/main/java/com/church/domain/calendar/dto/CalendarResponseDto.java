package com.church.domain.calendar.dto;


import com.church.domain.calendar.entity.CalendarSchedule;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CalendarResponseDto extends CalendarDto {
    private Long calendarId;

    // CalendarDto 객체와 id를 매개변수로 받는 생성자
    public CalendarResponseDto(CalendarSchedule calendarSchedule) {
        super(
            calendarSchedule.getTitle(),
            calendarSchedule.getContent(),
            calendarSchedule.getStartTime(),
            calendarSchedule.getEndTime());
        this.calendarId = calendarSchedule.getId();
    }
}
