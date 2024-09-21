package com.Backend_v10.User;
import java.util.Optional;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findById(Long id);
    // Optional<User> findByName(String name);
    Optional<User> findByEmail(String Email);

    List<User> findByUsername(String username);
    
    @Query(value = "select articleid from articles where article_user_id in (\n" + 
                " select user2 from contacts \n" + 
                " where user1 = ?1 \n" +
                "  ) order by date_time_of_creation desc;", nativeQuery = true)
    List<Long> findContactArticles(Long id);



    @Query(value = "select articleid from articles where articles.article_user_id = ?1 order by date_time_of_creation desc;", nativeQuery = true)
    List<Long> findMyArticles(Long id);
}