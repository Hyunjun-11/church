package com.church.domain.calendar.service;

import com.church.domain.calendar.dto.CalendarRequestDto;
import com.church.domain.calendar.dto.CalendarResponseDto;
import com.church.domain.calendar.entity.CalendarSchedule;
import com.church.domain.calendar.repository.CalendarRepository;
import com.church.util.message.Message;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;

    //일정 전체조회
    @Transactional
    public ResponseEntity<Message<List<CalendarResponseDto>>> realAll() {
        List<CalendarSchedule> calendarList = calendarRepository.findAll();
        List<CalendarResponseDto> calendars = new ArrayList<>();
        for (CalendarSchedule calendar : calendarList) {
            calendars.add(new CalendarResponseDto(calendar));
        }
        return new ResponseEntity<>(new Message<>("일정 전체조회 성공",calendars),HttpStatus.OK);

    }
    //일정 조회
    @Transactional
    public ResponseEntity<Message<CalendarResponseDto>> readOne(Long id) {
        CalendarSchedule schedule=findById(id);
        CalendarResponseDto responseDto=new CalendarResponseDto(schedule);
        return new ResponseEntity<>(new Message<>("일정 조회 성공",responseDto),HttpStatus.OK);
    }
    //일정추가
    @Transactional
    public ResponseEntity<Message<CalendarResponseDto>> create(CalendarRequestDto calendarRequestDto) {

        CalendarSchedule schedule= CalendarSchedule.builder()
                .title(calendarRequestDto.getTitle())
                .content(calendarRequestDto.getContent())
                .startTime(calendarRequestDto.getStartTime())
                .endTime(calendarRequestDto.getEndTime())
                .build();
        calendarRepository.save(schedule);
        CalendarResponseDto responseDto=new CalendarResponseDto(schedule);
        return new ResponseEntity<>(new Message<>("일정 등록 성공",responseDto),HttpStatus.OK);

    }

    //일정수정
    @Transactional
    public ResponseEntity<Message<CalendarResponseDto>> updateCalendar(Long id, CalendarRequestDto calendarRequestDto) {
        CalendarSchedule schedule=findById(id);
        schedule.update(schedule);
        CalendarResponseDto responseDto=new CalendarResponseDto(schedule);

        return new ResponseEntity<>(new Message<>("일정 수정 성공",responseDto),HttpStatus.OK);
    }

    //일정 삭제
    @Transactional
    public String deleteCalendar(Long id) {
        CalendarSchedule schedule=findById(id);
        calendarRepository.deleteById(schedule.getId());
        return String.format("%d번째 일정 삭제 성공" ,schedule.getId());
    }
    private CalendarSchedule findById(Long id) {
        return calendarRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("해당 일정을 찾을 수 없습니다.")
        );
    }

}
