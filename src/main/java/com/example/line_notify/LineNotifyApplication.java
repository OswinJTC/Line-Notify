package com.example.line_notify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LineNotifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LineNotifyApplication.class, args);
	}

}
