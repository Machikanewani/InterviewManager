package com.example.InterviewManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InterviewManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewManagerApplication.class, args);
	}

}
