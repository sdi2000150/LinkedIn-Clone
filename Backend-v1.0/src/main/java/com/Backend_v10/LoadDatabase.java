package com.Backend_v10;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Articles.ArticleRepository;
import com.Backend_v10.Comments.Comment;
import com.Backend_v10.Comments.CommentRepository;
import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.JobApplication.JobApplicationRepository;
import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;
import com.Backend_v10.User.UserService;
import com.Backend_v10.UserConnection.UserConnection;
import com.Backend_v10.UserConnection.UserConnectionRepository;
import com.Backend_v10.Jobs.Job;
import com.Backend_v10.Jobs.JobRepository;



//Preload Database with certain users
@Configuration
public class LoadDatabase {

  @Autowired  // Inject UserRepository
  private UserRepository userRepo;


  @Autowired  // Inject UserRepository
  private UserConnectionRepository UserConnRepo;

  @Autowired  // Inject ArticleRepository
  private ArticleRepository articleRepo;

  @Autowired  // Inject CommentRepository
  private CommentRepository commentRepo;

  @Autowired  // Inject JobRepository
  private JobRepository jobRepo;

  @Autowired
  private JobApplicationRepository jobApplicationRepo;

  @Autowired  // Inject PasswordEncoder
  private PasswordEncoder encoder;

  @Autowired
  private UserService userService;

  public LoadDatabase(UserService userService, UserConnectionRepository UserConnRepo, UserRepository userRepo, ArticleRepository articleRepo, CommentRepository commentRepo, JobRepository jobRepo, JobApplicationRepository jobApplicationRepo, PasswordEncoder encoder) {
    this.userRepo = userRepo;
    this.articleRepo = articleRepo;
    this.jobRepo = jobRepo;
    this.jobApplicationRepo = jobApplicationRepo;
    this.encoder = encoder;
    this.commentRepo = commentRepo;
    this.userService = userService;
  }

  @Bean  
  CommandLineRunner initDatabase(){
    return args -> {

        // Create and save users first
        User admin1 = new User("teomor", "Theodoros", encoder.encode("1234"), "ROLE_ADMIN", "Moraitis", "teomor@email.com");
        User admin2 = new User("nickmosch", "Nikitas", encoder.encode("1234"), "ROLE_ADMIN", "Moschos", "nickmosch@email.com");
        User user1 = new User("bobross", "Bob", encoder.encode("1234"), "ROLE_USER", "Ross", "bobross@email.com");
        User user2 = new User("jetlee", "Jet", encoder.encode("1234"), "ROLE_USER", "Lee", "jetlee@email.com");
        User user3 = new User("TC", "Thomas", encoder.encode("1234"), "ROLE_USER", "Charles", "thomasch@email.com");
        User user4 = new User("TC", "Taylor", encoder.encode("1234"), "ROLE_USER", "Carlson", "taylorcar@email.com");
        // Save users to the repository
        userRepo.save(admin1);
        userRepo.save(admin2);
        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);
        
        // [SOLVED] PROBLEM occurs, with user who owns the article/job (JSON infinite creation) 
        // -> SOLUTION: @JsonManagedReference/@JsonBackReference in all involved entities 
                    //  OR @JsonIgnoreProperties in all involved entities

        // Create articles and jobs
        Article article1 = new Article("Just got my First Job!!", null);
        Article article2 = new Article("Just got my Second Job!!", null);
        Job job1 = new Job("In need of a Software Engineer");
        Job job2 = new Job("In need of a Data Scientist");
        // Save articles and jobs to the repository
        articleRepo.save(article1);
        articleRepo.save(article2);
        jobRepo.save(job1);
        jobRepo.save(job2);
        
        // Associate articles and jobs with users
        userService.addArticle(user1, article1);
        userService.addArticle(user1, article2);
        userService.addJob(user1, job1);
        userService.addJob(user1, job2);


        // Create comments and save them to the repository
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setContent("Nice Post");
        comment2.setContent("I Agree Great Post");

        commentRepo.save(comment1);
        commentRepo.save(comment2);
        //Assosiate comments with articles/users
        userService.addComment(article1, user2, comment1);
        userService.addComment(article2, user2, comment2);

        // Create jobapplications and save them to the repository
        JobApplication jobApplication1 = new JobApplication();
        JobApplication jobApplication2 = new JobApplication();
        jobApplicationRepo.save(jobApplication1);  
        jobApplicationRepo.save(jobApplication2);
        // Assosiate jobapplications with jobs/users
        userService.addJobApplication(job1, user2, jobApplication1);
        userService.addJobApplication(job2, user2, jobApplication2);

        // Contacts tests:
        userService.addContact(user1, user2);
        userService.addContact(user1, user3);
        userService.addContact(user1, user4);

        // UserConnections tests:
        UserConnection conn = new UserConnection();
        conn.setUser1(user3.getEmail());
        conn.setUser2(user4.getEmail());
        UserConnRepo.save(conn);

        //userRepo.save(user2);
        //UserConnRepo.delete(conn);
        List<String> Res = UserConnRepo.findUsersRequestingMe("jetlee@email.com");
        System.out.println(Res);
      };
  }
}