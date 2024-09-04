package com.Backend_v10.Jobs;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {
    private JobRepository repository;

    JobController(JobRepository repository){
        this.repository = repository;
    }


    //...
}
