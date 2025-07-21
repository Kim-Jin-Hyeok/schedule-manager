package com.example.schedulemanager.service;

import com.example.schedulemanager.domain.User;
import com.example.schedulemanager.dto.LoginRequestDto;
import com.example.schedulemanager.dto.UserRequestDto;
import com.example.schedulemanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public User register(UserRequestDto dto) {
		if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new RuntimeException("이미 존재하는 이메일 입니다.");
		}
		
		User user = User.builder()
				.email(dto.getEmail())
				.password(passwordEncoder.encode(dto.getPassword()))
				.name(dto.getName())
				.build();
		
		return userRepository.save(user);
	}
	
	public User authenticate(LoginRequestDto dto) {
		Optional<User> optionUser = userRepository.findByEmail(dto.getEmail());
		
		if(optionUser.isEmpty()) {
			throw new RuntimeException("존재하지 않는 사용자 입니다.");
		}
		
		User user = optionUser.get();
		
		if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
		
		return user;
	}
}
