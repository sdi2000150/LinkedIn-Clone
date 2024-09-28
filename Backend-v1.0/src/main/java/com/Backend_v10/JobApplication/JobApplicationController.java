package com.Backend_v10.JobApplication;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.User.UserRepository;

import jakarta.transaction.Transactional;

import com.Backend_v10.Jobs.JobRepository;
import com.Backend_v10.JobApplication.JobApplicationRepository;

import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.User.User;
import com.Backend_v10.Jobs.Job;

@RestController
@RequestMapping("/application")
public class JobApplicationController {
    private JobApplicationRepository JobApplicationRepo;
    private final UserRepository  UserRepo;
    private final JobRepository JobRepo;
    
    JobApplicationController(JobApplicationRepository JobApplicationRepo, UserRepository URepo, JobRepository jobRepo){
        this.JobApplicationRepo = JobApplicationRepo;
        this.UserRepo = URepo;
        this.JobRepo = jobRepo;
    }

    // Delete a certain job given its ID
    @Transactional
    @DeleteMapping("/{userEmail}/delete/{jobID}")
    public void deleteJobApplication(@PathVariable String userEmail, @PathVariable Long jobID){
        //Find user
        Optional<User> user = this.UserRepo.findByEmail(userEmail);
        Optional<Job> job = this.JobRepo.findById(jobID);

        //Find job
        //Find application based on job's apllications machting user's application
        JobApplication application_to_delete = this.JobApplicationRepo.GetApplicationOfJobWithUserID(jobID, user.get().getUserID());
        user.get().getMyJobApplications().remove(application_to_delete);
        this.JobApplicationRepo.DeleteApplicationsOfJobWithUserID(jobID, user.get().getUserID());
        job.get().getJobApplications().remove(application_to_delete);        

    }
    
}



