package com.church.domain.calendar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CalendarSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String startTime;

    @Column( nullable = false)
    String endTime;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String content;

    public void update(CalendarSchedule schedule){
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
    }
}
