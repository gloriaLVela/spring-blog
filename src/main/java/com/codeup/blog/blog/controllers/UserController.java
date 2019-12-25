package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.models.Post;
import com.codeup.blog.blog.models.User;
import com.codeup.blog.blog.repositories.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class UserController {

    //private final UserRepository users;

    private PasswordEncoder passwordEncoder;


    private final UserRepository userDao;


    public UserController( UserRepository userDao,PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerUser(Model viewModel) {
        viewModel.addAttribute("user", new User());
        return "/sign-up";
    }


    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User newUser) {
        String hash = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hash);
        userDao.save(newUser);
        return "redirect:/login";
    }


    @GetMapping("/logout")
    public String logoutUser() {
        return "redirect:/home";
    }

    @GetMapping("/myPosts")
    public String displayProfile(Model viewModel) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> posts = userDao.getOne(loggedUser.getId()).getPosts();
        userDao.getOne(loggedUser.getId()).getPosts();
        for (Post currentPost : posts) {
            currentPost.getCategories();
        }
        viewModel.addAttribute("user", userDao.getOne(loggedUser.getId()));
         viewModel.addAttribute("posts", posts);
        return "users/profile";
    }

}