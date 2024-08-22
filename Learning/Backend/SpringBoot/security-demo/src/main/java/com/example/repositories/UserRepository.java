package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByUsername(String username);

}
