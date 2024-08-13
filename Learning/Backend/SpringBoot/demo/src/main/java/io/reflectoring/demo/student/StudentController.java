package io.reflectoring.demo.student;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reflectoring.demo.classroom.Classroom;

import org.springframework.web.bind.annotation.PutMapping;



//resources for our api 
@RestController
//Global Mapping of url to Start From
@RequestMapping
public class StudentController {
    private final StudentRepository repository;
    
    StudentController(StudentRepository repository) {
        this.repository = repository;
    }
    // @GetMapping()
    // public String Hi(){
    //     return "Hiiii";
    // }
	@GetMapping("students")
	public List<Student> getStudents(){
		return this.repository.findAll();
	}
    @CrossOrigin(origins = "*")
    @GetMapping("{id}")
    public Optional<Student> GetStudentById(@PathVariable String id){
        System.out.println("Giving back id " + id);
        return this.repository.findById(Long.valueOf(id));
    }

    @PostMapping
    public void registerStudent(@RequestBody Student student){
        Optional<Student> found_student = this.repository.findByEmail(student.getEmail());
        if(found_student.isEmpty()){
            System.out.println("Student" + student.getName() + " Not Found and thus added");
            this.repository.save(student);
        }
        else
            System.out.println("Student" + student.getName() + " Found in DB");
        
    }

    @DeleteMapping(path = "{studentID}")
    public void deleteStudent(@PathVariable Long studentID){
        
        Optional<Student> found_student = this.repository.findById(studentID);
        if(found_student.isEmpty()){
            System.out.println("Student Not Found");
        }
        else{
        this.repository.deleteById(studentID);
        System.out.println("Student Found in DB");    }
        }

    @PutMapping("student/{id}")
    public String UpdateStudentName(@PathVariable Long id, @RequestBody Student student) {
        
        repository.findById(id).map(newStudent -> {
            newStudent.setName(student.getName());
            newStudent.setAge(student.getAge());
            newStudent.setEmail(student.getEmail());
            repository.save(newStudent);
            return "Changed";
        })
        .orElseGet(() -> {
            return "Not Existing User";
        });
        return "Done";
    }

    @PutMapping("{id}/classroom")
    public String ChangeClassroom(@PathVariable Long id, @RequestBody Classroom c) {
        repository.findById(id).map(newStudent -> {
            newStudent.setMyClassroom(c);

            repository.save(newStudent);
            return "Changed";
        })
        .orElseGet(() -> {
            return "Not Existing User";
        });
        return "Done";                
    }

    }

