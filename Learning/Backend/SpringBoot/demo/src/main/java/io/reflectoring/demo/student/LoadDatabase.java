package io.reflectoring.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.reflectoring.demo.classroom.Classroom;
import io.reflectoring.demo.classroom.ClassroomRepository;

//Preload Database with certain users
@Configuration

public class LoadDatabase {
  @Bean  
  CommandLineRunner initDatabase(StudentRepository Srepository, ClassroomRepository Crepository){
    return args -> {
        Classroom c = new Classroom(33, "George Giannakoopoulos");
        Srepository.save(new Student());
        Student s = new Student();
        s.setMyClassroom(c);
        Srepository.save(s);
        //Crepository.save(c);
        
      };
  }
}
