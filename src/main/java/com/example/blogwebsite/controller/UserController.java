package com.example.blogwebsite.controller;

import com.example.blogwebsite.model.User;
import com.example.blogwebsite.reponsitory.UserRepository;
import com.example.blogwebsite.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
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

    @RequestMapping(value = "/adduser", method = {RequestMethod.POST})
    public String createUser(@Valid @ModelAttribute("user") User user, Errors errors) {
        System.out.println(user);
        System.out.println(user.getRoles().getRoleId());
        boolean isSaved = false;
        if(user.getRoles().getRoleId() == 1) {
            isSaved = userService.createNewAdmin(user);
        } else {
            isSaved = userService.createNewUser(user);
        }

        if(isSaved) {
            return "redirect:/admin/users?create";
        } else {
            return "admin/adduser.html";
        }



    }

    @RequestMapping(value = "/deleteUser", method = {RequestMethod.GET})
    public String deleteUser(@RequestParam Integer id) {
        System.out.println(id);
        boolean isExitst = userRepository.existsById(id);
        if(isExitst) {
            userRepository.deleteById(id);
            return "redirect:/admin/users?delete";
        }

        return "redirect:/admin/users";
    }



}
