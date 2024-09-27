package com.Backend_v10.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend_v10.User.User;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired  // Inject UserInfoService
    private UserInfoService service;

    @Autowired  // Inject JwtService
    private JwtService jwtService;

    @Autowired  // Inject AuthenticationManager
    private AuthenticationManager authenticationManager;

    @PostMapping("/addNewUser")
    public Boolean addNewUser(@RequestBody User userInfo) {
        return service.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {   // Authenticate user and generate token
        Authentication authentication = authenticationManager.authenticate( // Authenticate user
            new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) { // If user is authenticated
            return jwtService.generateToken(authRequest.getEmail()); // Generate token (JWT) and return it
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
