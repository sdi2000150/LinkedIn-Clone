package io.reflectoring.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Preload Database with certain users
@Configuration

public class LoadDatabase {
  @Bean  
  CommandLineRunner initDatabase(StudentRepository repository){
    return args -> {
        repository.save(new Student());
        repository.save(new Student());
    };
  }
}
