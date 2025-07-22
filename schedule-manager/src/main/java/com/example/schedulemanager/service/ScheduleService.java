package com.example.schedulemanager.service;

import com.example.schedulemanager.domain.Schedule;
import com.example.schedulemanager.domain.User;
import com.example.schedulemanager.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	
	private final ScheduleRepository scheduleRepository;
	
	public Schedule createSchedule(Schedule schedule, User user) {
		schedule.setUser(user);
		return scheduleRepository.save(schedule);
	}
	
	public List<Schedule> getSchedules(User user){
		return scheduleRepository.findAllByUser(user);
	}
	
	public void deleteSchedule(Long id, User user) {
		Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
		
		if(!schedule.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("삭제 권한이 없습니다.");
		}
		
		scheduleRepository.delete(schedule);
	}
}
