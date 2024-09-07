package com.Backend_v10.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController

@RequestMapping("user")
public class UserController {
    private final UserRepository repository;


    UserController(UserRepository repository){
        this.repository = repository;
    }

    //FILL ALL MAPPINGS(GET,POST,DELETE,PUT)

    @GetMapping("/1")
    public ResponseEntity<User> GetUser() {
        //Optional<User> u = this.repository.findByEmail(Email);
        Optional<User> u = this.repository.findById(1L);
        //unwrap Optional with .get
        System.out.println("Giving back user" + u.get().getName());
        return ResponseEntity.ok(u.get());
    }
    
}
