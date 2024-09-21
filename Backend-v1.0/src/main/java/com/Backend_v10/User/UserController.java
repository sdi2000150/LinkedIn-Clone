package com.Backend_v10.User;

import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Jobs.Job;
import com.Backend_v10.UserConnection.UserConnection;
import com.Backend_v10.UserConnection.UserConnectionRepository;
import com.Backend_v10.Articles.ArticleRepository;

import ch.qos.logback.core.model.processor.PhaseIndicator;

import com.Backend_v10.JobApplication.JobApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.jar.Attributes.Name;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final UserConnectionRepository ConnectionRepo;
    @Autowired
    private ArticleRepository articleRepository;
    
    private final UserService service;

    UserController(ArticleRepository articleRepository, UserRepository repository, UserConnectionRepository UconnRepo, UserService service){
        this.repository = repository;
        this.ConnectionRepo = UconnRepo;
        this.service = service;
        this.articleRepository = articleRepository;
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

    //WORKS!!
    @GetMapping("/NewR/{myemail}/{useremail}")
    public void NewRequest(@PathVariable String myemail, @PathVariable String useremail){
        //add the 2 emails in the table of requests with that order
        this.service.addRequestPair(myemail, useremail);
    }

    //WORKS!!
    @GetMapping("/RejectReceivedR/{myemail}/{useremail}")
    public void RejectReceivedRequest(@PathVariable String myemail, @PathVariable String useremail){
        this.service.DeleteRec(useremail,myemail);
    }

    //WORKS!!
    @GetMapping("/AcceptReceivedR/{myemail}/{useremail}")
    public void AcceptReceivedRequest(@PathVariable String myemail, @PathVariable String useremail){
        //delete request
        this.service.DeleteRec(useremail,myemail);
        
        //and add contact
        Optional<User> u1 = this.repository.findByEmail(myemail);
        Optional<User> u2 = this.repository.findByEmail(useremail);
        this.service.addContact(u1.get(),u2.get());
           
    }
    
    //WORKS!!
    @GetMapping("/DeleteSentR/{myemail}/{useremail}")
    public void DeleteSentRequest(@PathVariable String myemail, @PathVariable String useremail){
        this.service.DeleteRec(myemail,useremail);
        
    }


    // Get profile-view of a user
    @GetMapping("/view-profile/{email}")
    public ResponseEntity<User> getProfileView(@PathVariable String email) {
        Optional<User> u = this.repository.findByEmail(email);
        // logic here
        return ResponseEntity.ok(u.get());
    }

    //Find Relationship with User
    @GetMapping("/identify/{myemail}/{useremail}")
    public String IdentifyUser(@PathVariable String myemail, @PathVariable String useremail){
            return this.service.Identify_Connection(myemail, useremail);
    }

    //GET ALL Articles from my CONTACTS
    @GetMapping("/{email}/contact_articles")
    public ResponseEntity<List<Article>> GetContactArticles(@PathVariable String email){
        //Optional<User> userOptional = this.repository.findByEmail(email);
        
        return ResponseEntity.ok(service.return_articles_of_contacts(email));
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
    @PutMapping("/{email}/profile")
    public ResponseEntity<Boolean> UpdateUser(@PathVariable String email, @RequestBody User updatedUser) {
        Optional<User> userOptional = repository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (updatedUser.getUsername() != null) {
                user.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getName() != null) {
                user.setName(updatedUser.getName());
            }
            if (updatedUser.getLastname() != null) {
                user.setLastname(updatedUser.getLastname());
            }
            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPhone() != null) {
                user.setPhone(updatedUser.getPhone());
            }
            if (updatedUser.getBirthdate() != null) {
                user.setBirthdate(updatedUser.getBirthdate());
            }
            if (updatedUser.getCvFile() != null) {
                user.setCvFile(updatedUser.getCvFile());
            }
            if (updatedUser.getProfilePhoto() != null) {
                user.setProfilePhoto(updatedUser.getProfilePhoto());
            }
            if (updatedUser.getCoverPhoto() != null) {
                user.setCoverPhoto(updatedUser.getCoverPhoto());
            }
            if (updatedUser.getAbout() != null) {
                user.setAbout(updatedUser.getAbout());
            }
            if (updatedUser.getExperience() != null) {
                user.setExperience(updatedUser.getExperience());
            }
            if (updatedUser.getExperienceDescription() != null) {
                user.setExperienceDescription(updatedUser.getExperienceDescription());
            }
            if (updatedUser.getEducation() != null) {
                user.setEducation(updatedUser.getEducation());
            }
            if (updatedUser.getEducationDescription() != null) {
                user.setEducationDescription(updatedUser.getEducationDescription());
            }
            if (updatedUser.getSkills() != null) {
                user.setSkills(updatedUser.getSkills());
            }
            repository.save(user);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    // Endpoint to get all contacts
    @GetMapping("/{email}/contacts")
    public ResponseEntity<List<String[]>> getContacts(@PathVariable String email) {
        Optional<User> userOptional = this.repository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<User> contacts = user.getMyContacts();
            List<String[]> contactsInfo = new ArrayList<>();
            for (User contact : contacts) {
                String[] contactInfo = new String[5];
                contactInfo[0] = contact.getUsername();
                contactInfo[1] = contact.getEmail();
                contactInfo[2] = contact.getName();
                contactInfo[3] = contact.getLastname();
                contactInfo[4] = contact.getExperienceDescription();
                contactsInfo.add(contactInfo);
            }

            return ResponseEntity.ok(contactsInfo);
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
    
    // Endpoint to get all contacts' job offers
    @GetMapping("/{email}/contacts-job-offers")
    public ResponseEntity<List<Job>> getContactsJobOffers(@PathVariable String email) {
        Optional<User> userOptional = this.repository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<User> contacts = user.getMyContacts();
            List<Job> contactsJobOffers = new ArrayList<>();
            for (User contact : contacts) {
                contactsJobOffers.addAll(contact.getMyJobs());
            }
            return ResponseEntity.ok(contactsJobOffers);
        } else {
            // Handle the case where the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint to get all liked articles of a user
    @GetMapping("/{email}/liked-articles")
    public ResponseEntity<List<Article>> getLikedArticles(@PathVariable String email) {
        Optional<User> userOptional = this.repository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Article> likedArticles = user.getLikedArticles();
            return ResponseEntity.ok(likedArticles);
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

    // needs to be tested
    @GetMapping("/{email}/like/{articleID}")
    public boolean likeArticle(@PathVariable String email, @PathVariable Long articleID) {
        Optional<User> u = repository.findByEmail(email);
        Optional<Article> a = articleRepository.findById(articleID);
    
        if (u.isPresent() && a.isPresent()) {
            User user = u.get();
            Article article = a.get();

            // Check if the user has already liked the article
            if (user.getLikedArticles().contains(article)) {
                return false;
            }

            user.likeArticle(article);
            repository.save(user);
            // articleRepository.save(article);
            return true;
        } else {
            return false;
        }
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
