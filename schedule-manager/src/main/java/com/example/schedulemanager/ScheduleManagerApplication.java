package com.example.schedulemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.schedulemanager.domain.User;
import org.springframework.boot.CommandLineRunner;
import com.example.schedulemanager.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScheduleManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleManagerApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner testUser(UserRepository userRepository) {
		return args -> {
			if(userRepository.findByEmail("test@example.com").isEmpty()) {
				User user = User.builder().email("test@example.com").password("test").name("user").build();
				
				userRepository.save(user);
				System.out.println("테스트 유저 저장");
			}
			else {
				System.out.println("유저 존재");
			}
		};
	}

}
