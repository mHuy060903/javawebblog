package com.example.blogwebsite.controller;

import com.example.blogwebsite.model.User;
import com.example.blogwebsite.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("admin")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @RequestMapping(value = "/users", method = {RequestMethod.GET})
    public ModelAndView displayUsers(Model model, Authentication authentication) {
        List<User> users = userRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/users.html");
        User user = userRepository.readByEmail(authentication.getName());
        modelAndView.addObject("userId", user.getId());
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @RequestMapping(value = "/users/add", method = {RequestMethod.GET})
    public ModelAndView displayAddUsers(Model model) {
       ModelAndView modelAndView = new ModelAndView("admin/adduser.html");
       modelAndView.addObject("user", new User());
       return modelAndView;
    }
}
