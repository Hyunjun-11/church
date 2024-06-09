package com.church.domain.calendar.repository;

import com.church.domain.calendar.entity.CalendarSchedule;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CalendarRepository extends JpaRepository<CalendarSchedule, Long> {

}
