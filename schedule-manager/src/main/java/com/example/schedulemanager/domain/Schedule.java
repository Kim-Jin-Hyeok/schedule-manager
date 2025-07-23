package com.example.schedulemanager.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.schedulemanager.domain.enums.RepeatType;

@Entity
@Table(name = "schedules")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String description;
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	private Integer alarmMinutesBefore;
	
	@Enumerated(EnumType.STRING)
	private RepeatType repeatType;
	private Integer repeatCount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	public Schedule copy() {
		return Schedule.builder()
				.title(this.getTitle())
				.description(this.getDescription())
				.startTime(null)
				.endTime(null)
				.alarmMinutesBefore(this.getAlarmMinutesBefore())
				.repeatType(this.getRepeatType())
				.repeatCount(null)
				.user(this.getUser())
				.build();
	}
}
