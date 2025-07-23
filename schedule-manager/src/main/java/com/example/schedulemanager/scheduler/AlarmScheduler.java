package com.example.schedulemanager.scheduler;

import com.example.schedulemanager.domain.Schedule;
import com.example.schedulemanager.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmScheduler {
	
	private final ScheduleRepository scheduleRepository;
	
	@Scheduled(cron = "0 * * * * *")
	public void checkAlarms() {
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		
		List<Schedule> schedules = scheduleRepository.findAll();
		
		for(Schedule schedule : schedules) {
			if(schedule.getAlarmMinutesBefore() == null || schedule.getAlarmMinutesBefore() == 0) continue;
			
			LocalDateTime alaramTime = schedule.getStartTime().minusMinutes(schedule.getAlarmMinutesBefore());
			
			if(alaramTime.withSecond(0).withNano(0).equals(now)) {
				log.info("[알림] '{}' 일정이 '{}'분 후에 시작합니다.", schedule.getTitle(), schedule.getAlarmMinutesBefore());
			}
		}
	}

}
