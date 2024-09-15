package com.Backend_v10.User;

import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Jobs.Job;
import com.Backend_v10.UserConnection.UserConnection;
import com.Backend_v10.UserConnection.UserConnectionRepository;
import com.Backend_v10.JobApplication.JobApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.jar.Attributes.Name;
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
    private final UserConnectionRepository ConnectionRepo;


    UserController(UserRepository repository, UserConnectionRepository UconnRepo){
        this.repository = repository;
        this.ConnectionRepo = UconnRepo;
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

    //Get user Username and Email
    @GetMapping("/find_{username}")
    public List<String[]> getUsernameEmail(@PathVariable String username){
        
        
        List<User> u = this.repository.findByUsername(username);
        List<String[]> Results = new ArrayList<>();
        
        if( u.isEmpty() == true)
        return Results;
        
        
        for(int i = 0; i < u.size(); i++){
                String[] NameEmail = new String[2];
                String name = u.get(i).getUsername();
                String email = u.get(i).getEmail();
                NameEmail[0] = name;
                NameEmail[1] = email;
                Results.add(i, NameEmail);
        }

        return Results;
    }
    
    @GetMapping("/all_users")
    public List<String[]> getAllUsers(){

        List<User> u = this.repository.findAll();
        List<String[]> Results = new ArrayList<>();
        
        if( u.isEmpty() == true)
        return Results;
        
        
        for(int i = 0; i < u.size(); i++){
                String[] NameEmail = new String[2];
                String name = u.get(i).getUsername();
                String email = u.get(i).getEmail();
                NameEmail[0] = name;
                NameEmail[1] = email;
                Results.add(i, NameEmail);
        }

        return Results;
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

    // Endpoint to get all contacts
    @GetMapping("/{email}/contacts")
    public ResponseEntity<List<User>> getContacts(@PathVariable String email) {
        Optional<User> userOptional = this.repository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<User> contacts = user.getMyContacts();
            
            return  ResponseEntity.ok(contacts);
        } else {
            // Handle the case where the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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
    
    //Change Password and email 

    @PutMapping("/settings/change_{email}")
    public Boolean Change(@PathVariable String email, @RequestBody String[] EmailPassword) {

        Optional<User> u = repository.findByEmail(email);
        User user = u.get();
        String NewEmail = EmailPassword[0];
        
        //!!!TODO
        return true;
        // repository.findById(id).map(u -> {
        //     u.setName(updatedUser.getName());
        //     u.setLastname(updatedUser.getLastname());
        //     u.setEmail(updatedUser.getEmail());
        //     u.setBirthdate(updatedUser.getBirthdate());
        //     u.setCVFile(updatedUser.getCVFile());
        //     u.setPhoto(updatedUser.getPhoto());
        //     u.setUsername(updatedUser.getUsername());
        //     repository.save(u);
        //     return true;
        // })
        // .orElseGet(() -> {
        //     return false;
        // });
        // return false;
    }


    @PostMapping("/create_job/{owner_email}")
    public boolean CreateJob(@RequestBody Job newJob, @PathVariable String owner_email){
        
        // Optional<Job> found_job = this.repository.findById(newJob.getJobID());
        // if(found_job.isEmpty()){
        Optional<User> u = this.repository.findByEmail(owner_email);
        u.get().addJob(newJob);
        //j.set
        this.repository.save(u.get());
        //this.repository.save(newJob);
        return true;
    }


    @PostMapping("/create_article/{owner_email}")
    public boolean CreateArticle(@RequestBody Article newArticle, @PathVariable String owner_email){
        
        // Optional<Job> found_job = this.repository.findById(newJob.getJobID());
        // if(found_job.isEmpty()){
        Optional<User> u = this.repository.findByEmail(owner_email);
        u.get().addArticle(newArticle);
        newArticle.setDateTime_of_Creation(LocalDateTime.now());
        //j.set
        this.repository.save(u.get());
        //this.repository.save(newJob);
        return true;
    }
    // @GetMapping("/request_{from}")
    // public void sendConnectionRequest(@RequestParam String send_to, @PathVariable String from) {
    //     UserConnection connection = new UserConnection();

    //     Optional<User> sending = repository.findByEmail(from);
    //     Optional<User> accepting = repository.findByEmail(send_to);



    //     connection.setUser1(sending.get().getEmail());
    //     connection.setUser2(accepting.get().getEmail());
    //     connection.setPendingRequest(true);
    //     this.ConnectionRepo.save(connection);
    //     // this.connectionsInitiated.add(connection);
    //     // user.connectionsReceived.add(connection);
    // }
    // Endpoint to apply to a job
    // @PostMapping("/user/{email}/apply/{jobId}")
    // public ResponseEntity<?> applyToJob(@PathVariable String email, @PathVariable Long jobId) {
    //     User user = userService.getUserById(userId);
    //     Job job = jobService.getJobById(jobId);
    //     jobService.applyToJob(user, job);
    //     return ResponseEntity.ok().build();
    // }

}
