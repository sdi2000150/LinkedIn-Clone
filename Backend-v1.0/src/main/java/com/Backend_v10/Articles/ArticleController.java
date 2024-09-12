package com.Backend_v10.Articles;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.Comments.Comment;
import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleRepository repository;
    private final UserRepository  UserRepo;


    ArticleController(ArticleRepository repository, UserRepository URepo){
        this.repository = repository;
        this.UserRepo = URepo;
    }



    @GetMapping("{id}/comments")
    public List<Comment> GetArticleComments(@PathVariable String id) {
        Optional<Article> a = this.repository.findById(Long.parseLong(id));
        //unwrap Optional with .get
        List<Comment>  Comments = a.get().getArticleComments();
        return Comments;
    }
}


