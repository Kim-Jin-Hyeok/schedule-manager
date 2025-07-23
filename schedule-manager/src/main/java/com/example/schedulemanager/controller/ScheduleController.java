package com.example.schedulemanager.controller;

import com.example.schedulemanager.domain.Schedule;
import com.example.schedulemanager.domain.User;
import com.example.schedulemanager.dto.ScheduleRequestDto;
import com.example.schedulemanager.dto.ScheduleResponseDto;
import com.example.schedulemanager.exception.CustomException;
import com.example.schedulemanager.service.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	@PostMapping
	public Schedule createSchedule(@RequestBody ScheduleRequestDto dto, @AuthenticationPrincipal User user) {
		return scheduleService.createSchedule(dto, user);
	}
	
	@GetMapping
	public List<ScheduleResponseDto> getSchedules(@AuthenticationPrincipal User user){
		return scheduleService.getSchedules(user);
	}
	
	@DeleteMapping("/{id}")
	public String deleteSchedule(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
		scheduleService.deleteSchedule(id, user);
		return "삭제 완료";
	}
	
	@GetMapping(value = "/range")
	@Operation(summary ="날짜 범위로 일정 조회")
	public ResponseEntity<Map<LocalDate, List<ScheduleResponseDto>>> getScheduleByRange(
			@RequestParam(name = "month", required = false) String month,
			@RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
			@AuthenticationPrincipal User user
			){
		if(month != null) {
			YearMonth yearMonth = YearMonth.parse(month);
			LocalDate startDate = yearMonth.atDay(1);
			LocalDate endDate = yearMonth.atEndOfMonth();
			
			return ResponseEntity.ok(scheduleService.getScheduleGroupByDate(user, startDate, endDate));
		}
		
		if(start != null && end != null) {
			return ResponseEntity.ok(scheduleService.getScheduleGroupByDate(user, start, end));
		}
		
		throw new CustomException("month 혹은 start/end 설정을 확인하세요.", HttpStatus.BAD_REQUEST);
		
	}
}
