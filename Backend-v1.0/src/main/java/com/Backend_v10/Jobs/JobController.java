package com.Backend_v10.Jobs;

import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.User.UserRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

import com.Backend_v10.JobApplication.JobApplication;

@RestController
@RequestMapping("/job")
public class JobController {
    private JobRepository repository;
    private final UserRepository  UserRepo;

    JobController(JobRepository repository, UserRepository URepo){
        this.repository = repository;
        this.UserRepo = URepo;
    }

    @GetMapping("{id}/jobapplications")
    public List<JobApplication> GetJobComments(@PathVariable String id) {
        Optional<Job> a = this.repository.findById(Long.parseLong(id));
        //unwrap Optional with .get
        List<JobApplication>  jobApplications = a.get().getJobApplications();
        return jobApplications;
    }
}
