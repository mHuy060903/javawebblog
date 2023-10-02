package com.example.blogwebsite.controller;

import com.example.blogwebsite.model.User;

import com.example.blogwebsite.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("public")
public class PublicController {

    @Autowired
    UserService userService;
    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String displayRegister(Model model) {
      model.addAttribute("user", new User());
      return "register.html";
    }

    @RequestMapping(value = "/createUser", method = {RequestMethod.POST})
    public String createUser(@Valid @ModelAttribute("user") User user, Errors errors) {
        if(errors.hasErrors()){
            return "register.html";
        }
        boolean isSaved = userService.createNewUser(user);
        if(isSaved){
            return "redirect:/login?register";
        }else {
            return "register.html";
        }
    }
}
