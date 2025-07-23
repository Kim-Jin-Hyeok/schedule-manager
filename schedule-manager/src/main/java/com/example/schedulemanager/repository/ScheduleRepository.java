package com.example.schedulemanager.repository;

import com.example.schedulemanager.domain.Schedule;
import com.example.schedulemanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
	List<Schedule> findAllByUser(User user);
	List<Schedule> findAllByUserAndStartTimeBetween(User user, LocalDateTime start, LocalDateTime end);

}
