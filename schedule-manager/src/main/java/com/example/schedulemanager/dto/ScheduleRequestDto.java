package com.example.schedulemanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.example.schedulemanager.domain.Schedule;
import com.example.schedulemanager.domain.enums.RepeatType;
import com.example.schedulemanager.domain.User;

@Getter @Setter
public class ScheduleRequestDto {
	private String title;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer alarmMinutesBefore;
	private RepeatType repeatType;
	private Integer repeatCount;
	
	public Schedule toEntity(User user) {
		return Schedule.builder()
				.title(this.title)
				.description(this.description)
				.startTime(this.startTime)
				.endTime(this.endTime)
				.alarmMinutesBefore(this.alarmMinutesBefore)
				.repeatType(this.repeatType)
				.repeatCount(this.repeatCount)
				.user(user)
				.build();
	}
}
