package com.example.blogwebsite.controller;

import com.example.blogwebsite.model.Category;
import com.example.blogwebsite.model.Post;
import com.example.blogwebsite.model.User;
import com.example.blogwebsite.reponsitory.CateReponsitory;
import com.example.blogwebsite.reponsitory.PostReponsitory;
import com.example.blogwebsite.reponsitory.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DashBoardController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostReponsitory postReponsitory;

    @Autowired
    CateReponsitory cateReponsitory;

    @GetMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        User user = userRepository.readByEmail(authentication.getName());
        List<User> users = userRepository.findAll();
        List<Post> posts = postReponsitory.findAll();
        List<Category> categories = cateReponsitory.findAll();

        model.addAttribute("username", user.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        session.setAttribute("loggedInUser", user);
        model.addAttribute("numUsers", users.size());
        model.addAttribute("numPosts", posts.size());
        model.addAttribute("numCategories", categories.size());




        return "admin/index.html";
    }


}
