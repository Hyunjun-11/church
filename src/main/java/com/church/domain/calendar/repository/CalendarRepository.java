package com.church.domain.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

}
