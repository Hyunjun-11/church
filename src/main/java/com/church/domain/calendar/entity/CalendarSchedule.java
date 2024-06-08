package com.church.domain.calendar.entity;

import jakarta.persistence.*;

@Entity
public class CalendarSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String start;
    @Column(nullable = false)
    String end;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String content;
}
