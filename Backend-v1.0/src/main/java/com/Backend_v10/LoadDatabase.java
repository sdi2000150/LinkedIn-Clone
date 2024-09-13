package com.Backend_v10;
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
import com.Backend_v10.Jobs.Job;
import com.Backend_v10.Jobs.JobRepository;



//Preload Database with certain users
@Configuration
public class LoadDatabase {

  @Autowired  // Inject UserRepository
  private UserRepository userRepo;

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

  public LoadDatabase(UserRepository userRepo, ArticleRepository articleRepo, CommentRepository commentRepo, JobRepository jobRepo, JobApplicationRepository jobApplicationRepo, PasswordEncoder encoder) {
    this.userRepo = userRepo;
    this.articleRepo = articleRepo;
    this.jobRepo = jobRepo;
    this.jobApplicationRepo = jobApplicationRepo;
    this.encoder = encoder;
    this.commentRepo = commentRepo;
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
        // Create articles and jobs
        Article article1 = new Article("Just got my First Job!!", null);
        Article article2 = new Article("Just got my Second Job!!", null);
        Job job1 = new Job("In need of a Software Engineer");
        Job job2 = new Job("In need of a Data Scientist");

        // Associate articles and jobs with users
        user1.addArticle(article1);
        user1.addArticle(article2);
        // user2.AddArticle(article2);
        user1.addJob(job1);
        user1.addJob(job2);

        // Save users to the repository
        userRepo.save(admin1);
        userRepo.save(admin2);
        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);
        // Connect the two users... 
        // user1.sendConnectionRequest(user2);
        // user2.acceptConnectionRequest(user1);

        // PROBLEM occurs, with user who owns the article/job (JSON infinite creation) 
        // -> SOLUTION: @JsonManagedReference/@JsonBackReference in all involved entities 
                    //  OR @JsonIgnoreProperties in all involved entities

        // OLD VERSION Add Comment to article1 from user 2
        // article1.AddComment("Great Article, helped me a lot!",user2,article1);
        // articleRepo.save(article1);

        // NEW WAY
        Comment comment = new Comment();
        article1.addComment(comment);
        user2.addComment(comment);

        // Save the comment first to avoid duplication
        commentRepo.save(comment);

        articleRepo.save(article1);  
        userRepo.save(user2); // This will also save Comment because cascading is enabled in user


        
        // Create a JobApplication
        JobApplication jobApplication = new JobApplication();
        // Associate the jobApplication with the job and the user
        job1.addJobApplication(jobApplication);
        user2.addJobApplication(jobApplication);

        // Save the jobApplication first to avoid duplication
        jobApplicationRepo.save(jobApplication);

        // Save the job and the user(again) to update the relationships
        jobRepo.save(job1);  
        userRepo.save(user2); // This will also save jobApplication because cascading is enabled in user
      };
  }
}