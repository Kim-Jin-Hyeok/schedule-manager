package com.example.schedulemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.example.schedulemanager.domain.Schedule;
import com.example.schedulemanager.domain.enums.RepeatType;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResponseDto {
	private Long id;
	private String title;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer alarmMinutesBefore;
	private RepeatType repeatType;
	private Integer repeatCount;
	private String groupId;
	
	public static ScheduleResponseDto fromEntity(Schedule schedule) {
		return ScheduleResponseDto.builder()
				.id(schedule.getId())
				.title(schedule.getTitle())
				.description(schedule.getDescription())
				.startTime(schedule.getStartTime())
				.endTime(schedule.getEndTime())
				.alarmMinutesBefore(schedule.getAlarmMinutesBefore())
				.repeatType(schedule.getRepeatType())
				.repeatCount(schedule.getRepeatCount())
				.groupId(schedule.getGroupId())
				.build();
	}
}
