package com.example.uhavrend.repository;

import com.example.uhavrend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role,Long > {
    Role findByName(String name);
}
