package com.example.schedulemanager.scheduler;

import com.example.schedulemanager.domain.Schedule;
import com.example.schedulemanager.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmScheduler {
	
	private final ScheduleRepository scheduleRepository;
	private final TaskScheduler taskScheduler;
	
	public void scheduleAlarm(Schedule schedule) {
		if(schedule.getAlarmMinutesBefore() == null || schedule.getAlarmMinutesBefore() == 0) return;
		
		LocalDateTime alarmTime = schedule.getStartTime().minusMinutes(schedule.getAlarmMinutesBefore());
		Instant triggerTime = alarmTime.atZone(ZoneId.systemDefault()).toInstant();
		
		taskScheduler.schedule(() -> {
			System.out.printf("[알림] '%s' 일정이 '%d'분 후에 시작합니다.\n", schedule.getTitle(), schedule.getAlarmMinutesBefore());
		}, Date.from(triggerTime));
	}

}
