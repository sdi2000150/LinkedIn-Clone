package com.Backend_v10.JobApplication;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.User.UserRepository;
import com.Backend_v10.Jobs.JobRepository;
import com.Backend_v10.JobApplication.JobApplicationRepository;

import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.User.User;
import com.Backend_v10.Jobs.Job;

@RestController
@RequestMapping("/application")
public class JobApplicationController {
    private JobApplicationRepository JobApplicationRepo;
    private JobRepository JobRepo;
    private final UserRepository  UserRepo;
    
    JobApplicationController(JobApplicationRepository JobApplicationRepo, UserRepository URepo){
        this.JobApplicationRepo = JobApplicationRepo;
        this.UserRepo = URepo;
        this.JobRepo = JobRepo;
    }


    @DeleteMapping("/{userEmail}/delete/{jobID}")
    public void deleteJobApplication(@PathVariable String userEmail, @PathVariable Long jobID){
        
        //Find user
        Optional<User> user = this.UserRepo.findByEmail(userEmail);
        //Find job
        Optional<Job> job = this.JobRepo.findById(jobID);
        //Find application based on job's apllications machting user's application
        // for (JobApplication application : job.get().getJobApplications()) {
        //     if(application.getUser().equals(user.get())){

                //....
                //here need to be done the deletion of the application
                //from job and from user respectively
                //....

        //         System.out.println("JobApplication Found in DB");
        //         return;
        //     }
        // }
        System.out.println("JobApplication Not Found");

        // Optional<JobApplication> found_application = this.JobApplicationRepo.findById(id);
        // if(found_application.isEmpty()){
        //     System.out.println("JobApplication Not Found");
        // }
        // else{
        //     this.JobApplicationRepo.deleteById(id);
        //     System.out.println("JobApplication Found in DB");    
        // }
    }
    
}



