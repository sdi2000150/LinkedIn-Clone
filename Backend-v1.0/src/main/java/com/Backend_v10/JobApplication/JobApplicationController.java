package com.Backend_v10.JobApplication;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.User.UserRepository;

@RestController
@RequestMapping("/application")
public class JobApplicationController {
    private JobApplicationRepository repository;
    private final UserRepository  UserRepo;
    
    JobApplicationController(JobApplicationRepository repository, UserRepository URepo){
        this.repository = repository;
        this.UserRepo = URepo;
    }


    @DeleteMapping("/delete/{id}")
    public void deleteJobApplication(@PathVariable Long id){
        
        Optional<JobApplication> found_application = this.repository.findById(id);
        if(found_application.isEmpty()){
            System.out.println("JobApplication Not Found");
        }
        else{
            this.repository.deleteById(id);
            System.out.println("JobApplication Found in DB");    
        }
    }
    
}



