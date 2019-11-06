package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {

    ArrayList<Post> postList;

    public PostController() {
        postList = new ArrayList<Post>();

        postList.add(new Post(1, "first ad", "new"));
        postList.add(new Post(1, "second ad", "new"));
        postList.add(new Post(2, "third ad", "used"));
    }


    @GetMapping("/posts")
    public String index(Model viewModel) {

        viewModel.addAttribute("posts", postList);

        return "posts/index";
    }


    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", postList.get((int) id - 1));
        return "posts/show";
    }



    @PostMapping(path = "/posts/create")
    @ResponseBody
    public String createPost(@RequestParam String title, @RequestParam String body) {
        System.out.println("title = " + title);
        System.out.println("body = " + body);
        System.out.println("create a new post print");
        return "create a new post";
    }

}
