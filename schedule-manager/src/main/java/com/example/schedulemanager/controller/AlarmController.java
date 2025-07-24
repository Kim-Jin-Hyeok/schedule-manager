package com.example.schedulemanager.controller;

import com.example.schedulemanager.domain.Alarm;
import com.example.schedulemanager.domain.User;
import com.example.schedulemanager.repository.AlarmRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmController {
	
	private final AlarmRepository alarmRepository;
	
	@GetMapping
	public List<String> getUserAlarms(@AuthenticationPrincipal User user){
		return alarmRepository.findByUserIdAndCheckedFalse(user.getId()).stream().map(Alarm::getMessage).toList();
	}
}
