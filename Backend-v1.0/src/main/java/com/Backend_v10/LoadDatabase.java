package com.Backend_v10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Articles.ArticleRepository;
import com.Backend_v10.Comments.Comment;
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

  @Autowired  // Inject JobRepository
  private JobRepository jobRepo;

  @Autowired
  private JobApplicationRepository jobApplicationRepo;

  @Autowired  // Inject PasswordEncoder
  private PasswordEncoder encoder;

  public LoadDatabase(UserRepository userRepo, ArticleRepository articleRepo, JobRepository jobRepo, JobApplicationRepository jobApplicationRepo, PasswordEncoder encoder) {
    this.userRepo = userRepo;
    this.articleRepo = articleRepo;
    this.jobRepo = jobRepo;
    this.jobApplicationRepo = jobApplicationRepo;
    this.encoder = encoder;
  }

  @Bean  
  CommandLineRunner initDatabase(){
    return args -> {



        // Create and save users first
        User admin1 = new User("teomor", "Theodoros", encoder.encode("1234"), "ROLE_ADMIN", "Moraitis", "teomor@email.com");
        User admin2 = new User("nickmosch", "Nikitas", encoder.encode("1234"), "ROLE_ADMIN", "Moschos", "nickmosch@email.com");
        User user1 = new User("bobross", "Bob", encoder.encode("1234"), "ROLE_USER", "Ross", "bobross@email.com");
        User user2 = new User("jetlee", "Jet", encoder.encode("1234"), "ROLE_USER", "Lee", "jetlee@email.com");
        
        // Create articles and jobs
        Article article1 = new Article("Just got my First Job!!", null);
        Article article2 = new Article("Just got my Second Job!!", null);
        Job job1 = new Job("My first job offer");
        Job job2 = new Job("My second job offer");

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


        // problem occurs, with user who owns the article/job (JSON infinite creation) 
        // -> solution: @JsonManagedReference/@JsonBackReference in all involved entities 
                    //  OR @JsonIgnoreProperties in all involved entities

        //Add Comment to article1 from user 2
        // article1.AddComment("Great Article, helped me a lot!",user2,article1);
        // articleRepo.save(article1);

        // Create a JobApplication for user2 applying to job1
        JobApplication jobApplication = new JobApplication();
        //User2 applies to job1 (posted by user1)
        user2.addJobApplication(jobApplication);
        job1.addJobApplication(jobApplication);
        //Save the job and the user(again) to update the relationships
        jobRepo.save(job1);  
        userRepo.save(user2); // This will also save jobApplication because cascading is enabled in user
      };
  }
}