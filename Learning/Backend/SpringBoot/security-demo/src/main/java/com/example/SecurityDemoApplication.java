package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.example.models.Role;
import com.example.models.User;
import com.example.services.UserService;
import java.util.HashSet;

@SpringBootApplication
public class SecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser(new User(null, "John Travolta", "johntravolta", "1234", new HashSet<Role>()));
			userService.saveUser(new User(null, "Will Smith", "willsmith", "1234", new HashSet<Role>()));
			userService.saveUser(new User(null, "Jim Carry", "jimcarry", "1234", new HashSet<Role>()));
			userService.saveUser(new User(null, "Arnold Schwarzenegger", "arnoldschwarzenegger", "1234", new HashSet<Role>()));

			userService.addRoleToUser("johntravolta", "ROLE_USER");
			userService.addRoleToUser("johntravolta", "ROLE_USER");
			userService.addRoleToUser("willsmith", "ROLE_ADMIN");
			userService.addRoleToUser("jimcarry", "ROLE_ADMIN");
			userService.addRoleToUser("arnoldschwarzenegger", "ROLE_USER");
		};
	}
}
