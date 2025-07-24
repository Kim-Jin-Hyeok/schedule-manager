package com.example.schedulemanager.service;

import com.example.schedulemanager.domain.Schedule;
import com.example.schedulemanager.domain.User;
import com.example.schedulemanager.domain.enums.RepeatType;
import com.example.schedulemanager.dto.ScheduleRequestDto;
import com.example.schedulemanager.dto.ScheduleResponseDto;
import com.example.schedulemanager.repository.ScheduleRepository;
import com.example.schedulemanager.scheduler.AlarmScheduler;

import jakarta.transaction.Transactional;

import com.example.schedulemanager.exception.CustomException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	
	private final ScheduleRepository scheduleRepository;
	private final AlarmScheduler alarmScheduler;
	
	public Schedule createSchedule(ScheduleRequestDto dto, User user) {
		Schedule schedule = dto.toEntity(user);
		
		if(dto.getRepeatType() == RepeatType.NONE || dto.getRepeatCount() == null || dto.getRepeatCount() <= 1) {
			return scheduleRepository.save(schedule);
		}
		
		
		List<Schedule> schedules = new ArrayList<>();
		for(int i = 0; i < dto.getRepeatCount(); i++) {
			Schedule repeated = schedule.copy();
			repeated.setStartTime(calculateNextTime(schedule.getStartTime(), dto.getRepeatType(), i));
			repeated.setEndTime(calculateNextTime(schedule.getEndTime(), dto.getRepeatType(), i));
			schedules.add(repeated);
		}
		scheduleRepository.saveAll(schedules);
		return schedules.get(0);
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
	
	@Transactional
	public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto, User user) {
		Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new CustomException("일정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
		
		if(!schedule.getUser().getId().equals(user.getId())) {
			throw new CustomException("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		if(schedule.getGroupId() != null) {
			scheduleRepository.deleteByGroupId(schedule.getGroupId());
		}
		else {
			scheduleRepository.delete(schedule);
		}
		
		Schedule created = createSchedule(dto, user); 
		
		return ScheduleResponseDto.fromEntity(created);
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
	
	private LocalDateTime calculateNextTime(LocalDateTime time, RepeatType type, int offset) {
		return switch(type) {
		case DAILY -> time.plusDays(offset);
		case WEEKLY -> time.plusWeeks(offset);
		case MONTHLY -> time.plusMonths(offset);
		default -> time;
		};
	}
}
