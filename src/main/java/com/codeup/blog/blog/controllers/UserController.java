package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserRepository userDao ;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users/test")
    public String registerUser() {

        return "users/register";
    }



}
