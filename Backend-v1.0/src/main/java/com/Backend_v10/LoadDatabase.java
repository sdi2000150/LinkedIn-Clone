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

  @Autowired  // Inject PasswordEncoder
  private PasswordEncoder encoder;

  @Bean  
  CommandLineRunner initDatabase(UserRepository UserRepo){
    return args -> {
        // Classroom c = new Classroom(33, "George Giannakoopoulos");
        // Srepository.save(new Student());
        // Student s = new Student();
        // s.setMyClassroom(c);
        // Srepository.save(s);
        //Crepository.save(c);
        
        User admin1 = new User("teomor", "Theodoros", encoder.encode("1234"), "ROLE_ADMIN", "Moraitis", "teomor@email.com");
        Article article = new Article("Just got my First Job!!", null);
        // ArticleRepo.save(article);
        admin1.AddArticle(article);

        User admin2 = new User("nickmosch", "Nikitas", encoder.encode("1234"), "ROLE_ADMIN", "Moschos", "nickmosch@email.com");
        // ArticleRepo.save(article);
        admin2.AddArticle(article);

        User user1 = new User("bobross", "Bob", encoder.encode("1234"), "ROLE_USER", "Ross", "bobross@email.com");
        user1.AddArticle(article);
        User user2 = new User("jetlee", "Jet", encoder.encode("1234"), "ROLE_USER", "Lee", "jetlee@email.com");
        user2.AddArticle(article);


        UserRepo.save(admin1);
        UserRepo.save(admin2);
        UserRepo.save(user1);
      };
  }
}