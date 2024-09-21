package com.Backend_v10.Jobs;

import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.JobApplication.JobApplicationRepository;

@RestController
@RequestMapping("/job")
public class JobController {
    private JobRepository repository;
    private final UserRepository  UserRepo;
    private final JobApplicationRepository JobAppRepo;

    JobController(JobRepository repository, UserRepository URepo, JobApplicationRepository jobAppRepo){
        this.repository = repository;
        this.UserRepo = URepo;
        this.JobAppRepo = jobAppRepo;
    }

    @GetMapping("{id}/jobapplications")
    public List<JobApplication> GetJobComments(@PathVariable String id) {
        Optional<Job> a = this.repository.findById(Long.parseLong(id));
        //unwrap Optional with .get
        List<JobApplication>  jobApplications = a.get().getJobApplications();
        return jobApplications;
    }

    
    @DeleteMapping("/delete/{id}")
    public void deleteJob(@PathVariable Long id){
        //delete all applications on this job 
        this.JobAppRepo.DeleteApplicationsOfJob(id);
        //for(app: )

        Optional<Job> found_job = this.repository.findById(id);
        if(found_job.isEmpty()){
            System.out.println("Job Not Found");
        }
        else{
            this.repository.deleteById(id);
            System.out.println("Job Found in DB");    
        }
    }



    
}
