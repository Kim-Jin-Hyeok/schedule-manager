package com.example.schedulemanager.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScheduleResponseDto {
	private Long id;
	private String title;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
