package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserRepository userDao;

    public HomeController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/")
    public String index(Model viewModel) {
        viewModel.addAttribute("users", userDao.findAll());
        return "home";
    }
}
