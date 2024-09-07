package com.Backend_v10;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;



//Preload Database with certain users
@Configuration

public class LoadDatabase {
  @Bean  
  CommandLineRunner initDatabase(UserRepository UserRepo){
    return args -> {
        // Classroom c = new Classroom(33, "George Giannakoopoulos");
        // Srepository.save(new Student());
        // Student s = new Student();
        // s.setMyClassroom(c);
        // Srepository.save(s);
        //Crepository.save(c);
        User u = new User("theomor", "Theodoros", "Moraitis", "theomor@gmail.com");
        UserRepo.save(u);
      };
  }
}