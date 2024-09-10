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
        
        User u = new User("teomor", "Theodoros", encoder.encode("1234"), "ROLE_ADMIN", "Moraitis", "teomor@email.com");
        Article article = new Article("Just got my First Job!!", null);
       // ArticleRepo.save(article);
        u.AddArticle(article);
        
        UserRepo.save(u);
      };
  }
}