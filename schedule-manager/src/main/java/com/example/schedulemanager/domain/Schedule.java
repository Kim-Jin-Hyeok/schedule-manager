package com.example.schedulemanager.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
}
