package com.example.blogwebsite.service;

import com.example.blogwebsite.model.Roles;
import com.example.blogwebsite.model.User;
import com.example.blogwebsite.reponsitory.RoleReponsitory;
import com.example.blogwebsite.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userReponsitory;

    @Autowired
    private RoleReponsitory roleReponsitory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewUser(User user) {
        boolean isSaved = false;
        Roles role = roleReponsitory.getByRoleName("USER");
        user.setRoles(role);
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        user = userReponsitory.save(user);
        if(null != user && user.getId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }

    public boolean createNewAdmin(User user) {
        boolean isSaved = false;
        Roles role = roleReponsitory.getByRoleName("ADMIN");
        user.setRoles(role);
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        user = userReponsitory.save(user);
        if(null != user && user.getId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }

}
