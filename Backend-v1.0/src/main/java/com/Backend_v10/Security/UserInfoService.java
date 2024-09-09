package com.Backend_v10.Security;

import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Handles user-related operations, including authentication and user data management
@Service    // Indicates that this class is a service
public class UserInfoService implements UserDetailsService {    // Implementing UserDetailsService interface

    @Autowired  // Inject UserInfoRepository
    private UserRepository repository;

    @Autowired  // Inject PasswordEncoder
    private PasswordEncoder encoder;

    @Override   // Overriding the loadUserByUsername method from UserDetailsService
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Optional<UserInfo> userDetail = repository.findByEmail(username); // Assuming 'email' is used as username
        Optional<User> userDetail = repository.findByName(username); // Assuming 'username' is used as username

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    // Add a new user
    public String addUser(User userInfo) {
        // Encode password before saving the user
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }

    // Get user by username
    public User getUser(String username) {
        return repository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
