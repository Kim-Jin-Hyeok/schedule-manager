package com.example.schedulemanager.controller;

import com.example.schedulemanager.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@GetMapping("/secure")
	public String secureTest(@AuthenticationPrincipal User user) {
		return "인증된 사용자 : " + user.getEmail();
	}

}
