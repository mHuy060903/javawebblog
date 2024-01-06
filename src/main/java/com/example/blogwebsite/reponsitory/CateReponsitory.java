package com.example.blogwebsite.reponsitory;

import com.example.blogwebsite.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateReponsitory extends JpaRepository<Category, Integer> {
}
