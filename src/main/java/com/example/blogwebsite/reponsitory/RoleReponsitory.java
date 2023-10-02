package com.example.blogwebsite.reponsitory;

import com.example.blogwebsite.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleReponsitory extends JpaRepository<Roles, Integer> {
    Roles getByRoleName(String roleName);
}
