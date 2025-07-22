package com.example.schedulemanager.controller;

import com.example.schedulemanager.domain.Schedule;
import com.example.schedulemanager.domain.User;
import com.example.schedulemanager.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	@PostMapping
	public Schedule createSchedule(@RequestBody Schedule schedule, @AuthenticationPrincipal User user) {
		return scheduleService.createSchedule(schedule, user);
	}
	
	@GetMapping
	public List<Schedule> getSchedules(@AuthenticationPrincipal User user){
		return scheduleService.getSchedules(user);
	}
	
	@DeleteMapping("/{id}")
	public String deleteSchedule(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
		scheduleService.deleteSchedule(id, user);
		return "삭제 완료";
	}
}
