package io.reflectoring.demo.classroom;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reflectoring.demo.student.Student;
import io.reflectoring.demo.student.StudentRepository;

@RestController
@RequestMapping
public class ClassroomController {
    
    private final ClassroomRepository repository;

    ClassroomController(ClassroomRepository repository){
        this.repository = repository;
    }

    @GetMapping("/classrooms")
	public List<Classroom> getClassrooms(){
		return this.repository.findAll();
	}

   

  

    
    
}
