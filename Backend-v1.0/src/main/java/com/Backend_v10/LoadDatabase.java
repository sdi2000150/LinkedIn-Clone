package com.Backend_v10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Articles.ArticleRepository;
import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;



//Preload Database with certain users
@Configuration
public class LoadDatabase {

  @Autowired  // Inject UserRepository
  private UserRepository userRepo;

  @Autowired  // Inject ArticleRepository
  private ArticleRepository articleRepo;

  @Autowired  // Inject PasswordEncoder
  private PasswordEncoder encoder;

  public LoadDatabase(UserRepository userRepo, ArticleRepository articleRepo, PasswordEncoder encoder) {
    this.userRepo = userRepo;
    this.articleRepo = articleRepo;
    this.encoder = encoder;
  }

  @Bean  
  CommandLineRunner initDatabase(){
    return args -> {
        // Classroom c = new Classroom(33, "George Giannakoopoulos");
        // Srepository.save(new Student());
        // Student s = new Student();
        // s.setMyClassroom(c);
        // Srepository.save(s);
        //Crepository.save(c);
        
        // Create and save articles first
        Article article1 = new Article("Just got my First Job!!", null);
        Article article2 = new Article("Just got my First Job!!", null);
        // articleRepo.save(article);

        // Create and save users
        User admin1 = new User("teomor", "Theodoros", encoder.encode("1234"), "ROLE_ADMIN", "Moraitis", "teomor@email.com");
        User admin2 = new User("nickmosch", "Nikitas", encoder.encode("1234"), "ROLE_ADMIN", "Moschos", "nickmosch@email.com");
        User user1 = new User("bobross", "Bob", encoder.encode("1234"), "ROLE_USER", "Ross", "bobross@email.com");
        User user2 = new User("jetlee", "Jet", encoder.encode("1234"), "ROLE_USER", "Lee", "jetlee@email.com");

        // Associate articles with users
        admin1.AddArticle(article1);
        admin2.AddArticle(article2);
        // user1.AddArticle(article);
        // user2.AddArticle(article);

        // Save users to the repository
        userRepo.save(admin1);
        userRepo.save(admin2);
        userRepo.save(user1);
        userRepo.save(user2);
      };
  }
}