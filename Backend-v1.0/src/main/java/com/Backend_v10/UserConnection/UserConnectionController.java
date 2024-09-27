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
}
