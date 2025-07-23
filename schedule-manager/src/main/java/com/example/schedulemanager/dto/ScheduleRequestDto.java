package com.example.schedulemanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ScheduleRequestDto {
	private String title;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer alarmMinutesBefore;
}
