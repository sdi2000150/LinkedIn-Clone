package com.Backend_v10.User;

import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Jobs.Job;
import com.Backend_v10.JobApplication.JobApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository repository;


    UserController(UserRepository repository){
        this.repository = repository;
    }

    //FILL ALL MAPPINGS(GET,POST,DELETE,PUT)

    //for testing
    @GetMapping("/1")
    public ResponseEntity<User> GetUser() {
        //Optional<User> u = this.repository.findByEmail(Email);
        Optional<User> u = this.repository.findById(1L);
        //unwrap Optional with .get
        System.out.println("Giving back user " + u.get().getMyArticles().size());
        return ResponseEntity.ok(u.get());
    }

    //get user info by email
    @GetMapping("/{email}")
    public ResponseEntity<User> GetUser(@PathVariable String email) {
        Optional<User> u = this.repository.findByEmail(email);
        //unwrap Optional with .get
        System.out.println("Giving back user " + u.get().getMyArticles().size());
        return ResponseEntity.ok(u.get());
    }

    //updates User fields. Returns true if its done properly, false else
    @PutMapping("/{id}")
    public Boolean UpdateUser(@PathVariable Long id, @RequestBody User updatedUser) {

        repository.findById(id).map(u -> {
            u.setName(updatedUser.getName());
            u.setLastname(updatedUser.getLastname());
            u.setEmail(updatedUser.getEmail());
            u.setBirthdate(updatedUser.getBirthdate());
            u.setCVFile(updatedUser.getCVFile());
            u.setPhoto(updatedUser.getPhoto());
            u.setUsername(updatedUser.getUsername());
            repository.save(u);
            return true;
        })
        .orElseGet(() -> {
            return false;
        });
        return false;
    }

    // Endpoint to get all jobs a specific user has applied for
    @GetMapping("/{email}/applied-jobs")
    public ResponseEntity<Job[]> getAppliedJobs(@PathVariable String email) {
        Optional<User> userOptional = this.repository.findByEmail(email);
        //get user's jobapplications
        //and then get the jobs from them
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<JobApplication> jobApplications = user.getMyJobApplications();

            Job[] appliedJobs = new Job[jobApplications.size()];
            for (int i = 0; i < jobApplications.size(); i++) {
                appliedJobs[i] = jobApplications.get(i).getJob();
            }
            
            return  ResponseEntity.ok(appliedJobs);
        } else {
            // Handle the case where the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{email}/job-offers")
    public ResponseEntity<List<Job>> getJobOffers(@PathVariable String email) {
        Optional<User> userOptional = this.repository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Job> jobOffers = user.getMyJobs();
            
            return ResponseEntity.ok(jobOffers);
        } else {
            // Handle the case where the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    

    // Endpoint to apply to a job
    // @PostMapping("/user/{email}/apply/{jobId}")
    // public ResponseEntity<?> applyToJob(@PathVariable String email, @PathVariable Long jobId) {
    //     User user = userService.getUserById(userId);
    //     Job job = jobService.getJobById(jobId);
    //     jobService.applyToJob(user, job);
    //     return ResponseEntity.ok().build();
    // }

}
