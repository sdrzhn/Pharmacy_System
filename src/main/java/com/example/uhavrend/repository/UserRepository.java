package com.example.uhavrend.repository;

import com.example.uhavrend.entity.Role;
import com.example.uhavrend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
