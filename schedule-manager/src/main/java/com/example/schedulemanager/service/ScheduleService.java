package com.example.schedulemanager.service;

import com.example.schedulemanager.domain.Schedule;
import com.example.schedulemanager.domain.User;
import com.example.schedulemanager.dto.ScheduleRequestDto;
import com.example.schedulemanager.dto.ScheduleResponseDto;
import com.example.schedulemanager.repository.ScheduleRepository;
import com.example.schedulemanager.exception.CustomException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	
	private final ScheduleRepository scheduleRepository;
	
	public Schedule createSchedule(ScheduleRequestDto dto, User user) {
		Schedule schedule = Schedule.builder()
				.title(dto.getTitle())
				.description(dto.getDescription())
				.startTime(dto.getStartTime())
				.endTime(dto.getEndTime())
				.alarmMinutesBefore(dto.getAlarmMinutesBefore())
				.user(user)
				.build();
		schedule.setUser(user);
		return scheduleRepository.save(schedule);
	}
	
	public List<ScheduleResponseDto> getSchedules(User user){
		return scheduleRepository.findAllByUser(user).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
	}
	
	public void deleteSchedule(Long id, User user) {
		Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new CustomException("일정이 존재하지 않습니다.", HttpStatus.NOT_FOUND));
		
		if(!schedule.getUser().getId().equals(user.getId())) {
			throw new CustomException("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		scheduleRepository.delete(schedule);
	}
	
	public ScheduleResponseDto convertToResponse(Schedule schedule) {
		return ScheduleResponseDto.builder()
				.id(schedule.getId())
				.title(schedule.getTitle())
				.description(schedule.getDescription())
				.startTime(schedule.getStartTime())
				.endTime(schedule.getEndTime())
				.alarmMinutesBefore(schedule.getAlarmMinutesBefore())
				.build();
	}
	
	public Map<LocalDate, List<ScheduleResponseDto>> getScheduleGroupByDate(User user, LocalDate start, LocalDate end){
		List<Schedule> schedules = scheduleRepository.findAllByUserAndStartTimeBetween(user, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
		
		return schedules.stream().map(ScheduleResponseDto::fromEntity).collect(Collectors.groupingBy(dto -> dto.getStartTime().toLocalDate(), TreeMap::new, Collectors.toList()));
	}
}
