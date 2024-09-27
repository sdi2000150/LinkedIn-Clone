package com.Backend_v10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.Backend_v10.ScheduledTasks.ScheduledTasks;
import com.Backend_v10.User.User;

import java.util.Arrays;

@SpringBootApplication
//@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);	
	}
}