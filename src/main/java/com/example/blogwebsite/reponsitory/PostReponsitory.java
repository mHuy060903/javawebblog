package com.example.blogwebsite.reponsitory;

import com.example.blogwebsite.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReponsitory extends JpaRepository<Post, Integer> {
}
