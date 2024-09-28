package com.Backend_v10.Comments;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.Jobs.JobRepository;
import com.Backend_v10.User.UserRepository;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private CommentRepository repository;
    private final UserRepository  UserRepo;

    CommentController(CommentRepository repository, UserRepository URepo){
        this.repository = repository;
        this.UserRepo = URepo;
    }


    //Deleting Certain Message using its ID
    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id){
        
        Optional<Comment> found_comment = this.repository.findById(id);
        if(found_comment.isEmpty()){
            System.out.println("Comment Not Found");
        }
        else{
            this.repository.deleteById(id);
        }
    }

}
