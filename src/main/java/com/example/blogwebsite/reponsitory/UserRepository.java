package com.example.blogwebsite.reponsitory;

import com.example.blogwebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User readByEmail(String email);
}
