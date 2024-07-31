package io.reflectoring.demo.student;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Spliterator.OfPrimitive;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name; 
    private Integer age;
    private LocalDate bod;
    private String email;

    public Student(){
        this.age  = 22;
        this.email = "sdi2000135@di.uoa.gr";
        this.name = "Nikitas";
    }
    public Student(String name){
        this.age  = 22;
        this.email = "sdi2000135@di.uoa.gr";
        this.name = name;
    }

    public Student(String name, String email){
        this.age  = 22;
        this.email = email;
        this.name = name;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDate getBod() {
        return bod;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public void setBod(LocalDate bod) {
        this.bod = bod;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", age=" + age + ", bod=" + bod + ", email=" + email + "]";
    }
    
}


