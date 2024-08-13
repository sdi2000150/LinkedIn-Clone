package io.reflectoring.demo.classroom;

import org.springframework.web.bind.annotation.GetMapping;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Integer studentNum;
    String ClassTeacher;

    public Classroom(){
    }
    public Classroom(Integer studentNum, String classTeacher) {
        this.studentNum = studentNum;
        this.ClassTeacher = classTeacher;
    }
    

    public Long getId() {
        return id;
    }
    public Integer getStudentNum() {
        return studentNum;
    }
    public String getClassTeacher() {
        return ClassTeacher;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }
    public void setClassTeacher(String classTeacher) {
        this.ClassTeacher = classTeacher;
    }

    @Override
    public String toString() {
        return "Classroom [id=" + id + ", studentNum=" + studentNum + ", ClassTeacher=" + ClassTeacher + "]";
    }


}
