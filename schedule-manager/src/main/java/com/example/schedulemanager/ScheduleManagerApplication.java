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

}
