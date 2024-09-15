package com.Backend_v10.UserConnection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.Articles.ArticleRepository;
import com.Backend_v10.User.UserRepository;

@RestController
@RequestMapping("/request")
public class UserConnectionController {
    private final UserConnectionRepository Connrepository;
    private final UserRepository  UserRepo;


    UserConnectionController(UserConnectionRepository repository, UserRepository URepo){
        this.Connrepository = repository;
        this.UserRepo = URepo;
    }

    @GetMapping("/create/{user1}_{user2}")
    public boolean AddConnection(@PathVariable("user1") String user1,@PathVariable("user2") String user2){
        UserConnection conn = new UserConnection();
        conn.setUser1(user1);
        conn.setUser2(user2);
        this.Connrepository.save(conn);
        return true;
    }

    @GetMapping("/accept/{user1}_{user2}")
    public boolean AcceptConnection(@PathVariable("user1") String user1,@PathVariable("user2") String user2){
        //UserConnection conn = new UserConnection();
        this.Connrepository.findUsersRequestingMe(user1);
        //conn.setUser1(user1);
        //conn.setUser2(user2);
        //this.Connrepository.save(conn);
        this.Connrepository.DeleteRequest(user1, user2);

        //Add to users Contacts 
        return true;
    }

    @GetMapping("/decline/{user1}_{user2}")
    public boolean DeclineConnection(@PathVariable("user1") String user1,@PathVariable("user2") String user2){
        //UserConnection conn = new UserConnection();
        this.Connrepository.findUsersRequestingMe(user1);
        //conn.setUser1(user1);
        //conn.setUser2(user2);
        //this.Connrepository.save(conn);
        this.Connrepository.DeleteRequest(user1, user2);
        return true;
    }
}
