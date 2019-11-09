package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model viewModel){

//        ArrayList<Post> postList = new ArrayList<Post>();
//
//        postList.add(new Post(1,"first ad", "new"));
//        postList.add(new Post(1,"second ad", "new"));
//        postList.add(new Post(1,"third ad", "used"));



        return "home";
    }
}
