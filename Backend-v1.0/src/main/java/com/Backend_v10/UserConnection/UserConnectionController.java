package com.Backend_v10.UserConnection;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.Articles.ArticleRepository;
import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;
import com.Backend_v10.User.UserService;

@RestController
@RequestMapping("/request")
public class UserConnectionController {
    private final UserConnectionRepository Connrepository;
    private final UserRepository  UserRepo;
    private final UserService uService;


    UserConnectionController(UserConnectionRepository repository, UserRepository URepo, UserService uService){
        this.Connrepository = repository;
        this.UserRepo = URepo;
        this.uService = uService;
    }


    //User1 REQUESTS User2 
    // @GetMapping("/create/{email1}/{email2}")
    // public boolean AddConnection(@PathVariable("email1") String user1,@PathVariable("email2") String user2){
    //     UserConnection conn = new UserConnection();
    //     conn.setUser1(user1);
    //     conn.setUser2(user2);
    //     this.Connrepository.save(conn);
    //     return true;
    // }

    //User2 ACCEPTS User1
   // @GetMapping("/accept/{email1}/{email2}")
    //public boolean AcceptConnection(@PathVariable("email1") String user1,@PathVariable("email2") String user2){
        //UserConnection conn = new UserConnection();
        // if( this.Connrepository.CheckIfRequestExists(user1, user2) == 1){
        //     //request exists and we accept it. 
        //     //So we delete it 
        //     this.Connrepository.DeleteRequest(user1, user2);
        //     //and add it to contacts
        //     Optional<User> u1 = this.UserRepo.findByEmail(user1);
        //     Optional<User> u2 = this.UserRepo.findByEmail(user2);

        //     this.uService.addContact(u1.get(),u2.get());

            
        //     return true;

        // }
        // return false;
        //this.Connrepository.findUsersRequestingMe(user1);
        //conn.setUser1(user1);
        //conn.setUser2(user2);
        //this.Connrepository.save(conn);

        //Add to users Contacts 
    //}

    // @GetMapping("/find_user_requesting_me/{email1}/")
    // public boolean AcceptConnection(@PathVariable("email1") String user1,@PathVariable("email2") String user2){
    //     //UserConnection conn = new UserConnection();

    //     this.Connrepository.findUsersRequestingMe(user1);
    //     //conn.setUser1(user1);
    //     //conn.setUser2(user2);
    //     //this.Connrepository.save(conn);
    //     this.Connrepository.DeleteRequest(user1, user2);

    //     //Add to users Contacts 
    //     return true;
    // }

    // @GetMapping("/find_my_requests/{email1}/")
    // public List<Users> UsersRequestingMe(@PathVariable("email1") String user1,@PathVariable("email2") String user2){
    //     //UserConnection conn = new UserConnection();

    //     return this.Connrepository.findUsersRequestingMe(user1);
    //     //conn.setUser1(user1);
    //     //conn.setUser2(user2);
    //     //this.Connrepository.save(conn);
    //     //this.Connrepository.DeleteRequest(user1, user2);

    //     //Add to users Contacts 
        
    // }


    // @GetMapping("/decline/{user1}/{user2}")
    // public boolean DeclineConnection(@PathVariable("user1") String user1,@PathVariable("user2") String user2){
    //     if( this.Connrepository.CheckIfRequestExists(user1, user2) == 1){
    //         //request exists and we decline it. 
    //         //So we delete it 
    //         this.Connrepository.DeleteRequest(user1, user2);      
    //         return true;
    //     }
    //     return false;
    // }
}
