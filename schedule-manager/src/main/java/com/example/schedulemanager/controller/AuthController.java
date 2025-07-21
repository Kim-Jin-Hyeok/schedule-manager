package com.example.schedulemanager.controller;

import com.example.schedulemanager.config.JwtUtil;
import com.example.schedulemanager.domain.User;
import com.example.schedulemanager.dto.LoginRequestDto;
import com.example.schedulemanager.dto.UserRequestDto;
import com.example.schedulemanager.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRequestDto dto){
		User user = userService.register(dto);
		return ResponseEntity.ok("회원 가입 성공 : " + user.getEmail());
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto dto){
		User user = userService.authenticate(dto);
		String token = jwtUtil.generateToken(user.getEmail());
		return ResponseEntity.ok().body("Bearer " + token);
	}
}
