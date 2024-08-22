package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.models.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String roleName);

}
