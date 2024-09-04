package com.Backend_v10.User;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserRepository repository;


    UserController(UserRepository repository){
        this.repository = repository;
    }

    //FILL ALL MAPPINGS(GET,POST,DELETE,PUT)

}
