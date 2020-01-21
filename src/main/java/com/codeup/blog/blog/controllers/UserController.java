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

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class UserController {

    //private final UserRepository users;

    private PasswordEncoder passwordEncoder;


    private final UserRepository userDao;


    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerUser(Model viewModel) {
        viewModel.addAttribute("user", new User());
        return "/sign-up";
    }


    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User newUser
            , Model viewModel) {
        String regexUS = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        String regexInternational = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        String regexEmail = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        String hash = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hash);
        // Verify the email format

        Pattern pattern = Pattern.compile(regexUS);
        pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(newUser.getEmail());

        if (!matcher.matches()) {
            viewModel.addAttribute("error", "Please provide a correct email format");
            return "register";
        }

        User duplicateEmail = userDao.findByEmail(newUser.getEmail());
        if (duplicateEmail != null){
            System.out.println("duplicate email");
            viewModel.addAttribute("duplicateEmail", "Please provide a different email");
            return "register";
        }

        // Check for duplicates
        newUser.setTime_stamp(new Date());
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
        viewModel.addAttribute("newLineChar", '\n');
        return "users/profile";
    }

}