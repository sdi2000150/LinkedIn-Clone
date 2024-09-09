package com.Backend_v10.User;
import java.util.Optional;

import com.Backend_v10.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findById(Long id);
    // Optional<User> findByName(String name);
    Optional<User> findByEmail(String Email);

    

}