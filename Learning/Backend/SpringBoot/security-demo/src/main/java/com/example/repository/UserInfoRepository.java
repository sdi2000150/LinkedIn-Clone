package com.example.repository;

import com.example.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // It interacts with the UserInfo entity in the database using JPA
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    // Optional<UserInfo> findByEmail(String email); // Use 'email' if that is the correct field for login

    Optional<UserInfo> findByName(String username); // Use 'name' if that is the correct field for login
}
