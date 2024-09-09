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
    public String addNewUser(@RequestBody User userInfo) {
        return service.addUser(userInfo);
    }

    // @GetMapping("/user/userProfile")
    // @PreAuthorize("hasAuthority('ROLE_USER')")  // Check if the user has the 'ROLE_USER' authority
    // public String userProfile() {
    //     return "Welcome to User Profile";
    // }

    // @GetMapping("/admin/adminProfile")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Check if the user has the 'ROLE_ADMIN' authority
    // public String adminProfile() {
    //     return "Welcome to Admin Profile";
    // }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {   // Authenticate user and generate token
        Authentication authentication = authenticationManager.authenticate( // Authenticate user
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) { // If user is authenticated
            return jwtService.generateToken(authRequest.getUsername()); // Generate token (JWT) and return it
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
